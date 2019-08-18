package com.codurance.Unit;

import com.codurance.model.Message;
import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.Commands;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;
import com.codurance.service.SocialNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class UserCommandsShould {

    private final LocalDateTime dateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 12, 19);
    private static final String ALICE_POST_MESSAGE = "Alice -> I love the weather today";
    private static final String BOB_POST_MESSAGE = "Bob -> Damn! We lost!";
    private static final String ALICE_WALL = "Alice wall";
    private static final String BOB_WALL = "Bob wall";

    private MessageRepository messageRepositoryMock;
    private SocialConsole consoleMock;
    private UserRepository userRepositoryMock;
    private LocalClock clockMock;

    @BeforeEach
    void setUp() {
        messageRepositoryMock = mock(MessageRepository.class);
        consoleMock = mock(SocialConsole.class);
        userRepositoryMock = mock(UserRepository.class);

        clockMock = mock(LocalClock.class);
        when(clockMock.now()).thenReturn(dateTime);
        when(userRepositoryMock.getFollowedUsers(new User("Charlie"))).thenReturn(Collections.singletonList("Alice"));

    }

    @Test
    void post_single_message_for_one_user() {
        Commands commands = new Commands(messageRepositoryMock, userRepositoryMock, clockMock, consoleMock);
        commands.post(ALICE_POST_MESSAGE);

        given(clockMock.now()).willReturn(dateTime);

        verify(messageRepositoryMock).addMessage(new Message("Alice", "I love the weather today", clockMock.now()));
    }

    @Test
    void return_one_message_from_users_wall() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(300);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);

        verify(consoleMock).print("Alice - I love the weather today (5 minutes ago)");
    }

    @Test
    void return_message_for_the_user_requesting_to_that_wall() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(120, 300);

        socialNetwork.messageParser(BOB_POST_MESSAGE);
        socialNetwork.messageParser(BOB_WALL);
        verify(consoleMock).print("Bob - Damn! We lost! (2 minutes ago)");

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);
        verify(consoleMock).print("Alice - I love the weather today (5 minutes ago)");
    }

    @Test
    void return_one_message_with_time_stamp() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(300);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice");
        verify(consoleMock).print("I love the weather today (5 minutes ago)");
    }

    @Test
    void return_two_messages_with_time_stamp() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(300, 240);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice -> It's raining!");
        socialNetwork.messageParser("Alice");

        verify(consoleMock).print("I love the weather today (5 minutes ago)");
        verify(consoleMock).print("It's raining! (4 minutes ago)");
    }

    @Test
    void return_two_messages_with_time_stamp_of_minutes_and_seconds() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(5, 240);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice -> It's raining!");
        socialNetwork.messageParser("Alice");

        verify(consoleMock).print("I love the weather today (5 seconds ago)");
        verify(consoleMock).print("It's raining! (4 minutes ago)");
    }

    @Test
    void allow_one_user_to_follow_another() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        socialNetwork.messageParser("Alice follows Bob");

        verify(userRepositoryMock).follow("Alice", "Bob");
    }

    @Test
    void return_posted_messages_for_users_being_followed() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(2, 300);
        given(userRepositoryMock.getFollowedUsers(new User("Charlie"))).willReturn(asList("Alice"));

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Charlie -> I'm in New York today! Anyone want to have a coffee?");
        socialNetwork.messageParser("Charlie follows Alice");
        socialNetwork.messageParser("Charlie wall");

        verify(consoleMock).print("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)");
        verify(consoleMock).print("Alice - I love the weather today (5 minutes ago)");
    }
}
