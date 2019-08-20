package com.codurance.Unit;

import com.codurance.model.Message;
import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class SocialNetworkShould {

    private static final String ALICE_POST_MESSAGE_WITH_TIME_STAMP_OF_FIVE_MINUTES = "Alice - I love the weather today (5 minutes ago)\n";
    private static final String MESSAGE_POSTED_BY_ALICE = "I love the weather today (5 minutes ago)\n";
    private final LocalDateTime dateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 12, 19);
    private static final String ALICE_POST_MESSAGE = "Alice -> I love the weather today";
    private static final String BOB_POST_MESSAGE = "Bob -> Damn! We lost!";
    private static final String ALICE_WALL = "Alice wall";
    private static final String ALICE_USERNAME = "Alice";
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
        when(userRepositoryMock.getFollowedUsers(new User("Charlie"))).thenReturn(Collections.singletonList(ALICE_USERNAME));

    }

    @Test
    void add_new_user_when_post_first_message() {
        SocialNetwork socialNetwork = new SocialNetwork(messageRepositoryMock, userRepositoryMock, clockMock, consoleMock);
        socialNetwork.messageParser(ALICE_POST_MESSAGE);

        verify(userRepositoryMock).addNewUser(new User("Alice"));
    }

    @Test
    void post_single_message_for_one_user() {
        SocialNetwork socialNetwork = new SocialNetwork(messageRepositoryMock, userRepositoryMock, clockMock, consoleMock);
        socialNetwork.messageParser(ALICE_POST_MESSAGE);

        given(clockMock.now()).willReturn(dateTime);

        verify(messageRepositoryMock).addMessage(new Message(ALICE_USERNAME, "I love the weather today", clockMock.now()));

    }

    @Test
    void return_one_message_from_users_wall() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(300);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);

        verify(consoleMock).print(ALICE_POST_MESSAGE_WITH_TIME_STAMP_OF_FIVE_MINUTES);
    }

    @Test
    void return_message_for_the_user_requesting_to_that_wall() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(120, 300);

        socialNetwork.messageParser(BOB_POST_MESSAGE);
        socialNetwork.messageParser(BOB_WALL);
        verify(consoleMock).print("Bob - Damn! We lost! (2 minutes ago)\n");

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);
        verify(consoleMock).print(ALICE_POST_MESSAGE_WITH_TIME_STAMP_OF_FIVE_MINUTES);
    }

    @Test
    void return_one_message_with_time_stamp() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(300);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_USERNAME);
        verify(consoleMock).print(MESSAGE_POSTED_BY_ALICE);
    }

    @Test
    void return_two_messages_with_time_stamp() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(300, 240);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice -> It's raining!");
        socialNetwork.messageParser(ALICE_USERNAME);

        verify(consoleMock).print(MESSAGE_POSTED_BY_ALICE);
        verify(consoleMock).print("It's raining! (4 minutes ago)\n");
    }

    @Test
    void return_two_messages_with_time_stamp_of_minutes_and_seconds() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(5, 240);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice -> It's raining!");
        socialNetwork.messageParser(ALICE_USERNAME);

        verify(consoleMock).print("I love the weather today (5 seconds ago)\n");
        verify(consoleMock).print("It's raining! (4 minutes ago)\n");
    }

    @Test
    void allow_one_user_to_follow_another() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        socialNetwork.messageParser("Alice follows Bob");

        verify(userRepositoryMock).follow(ALICE_USERNAME, "Bob");
    }

    @Test
    void return_posted_messages_for_users_being_followed() {
        MessageRepository messageRepository = new MessageRepository();
        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepositoryMock, clockMock, consoleMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(2, 300);
        given(userRepositoryMock.getFollowedUsers(new User("Charlie"))).willReturn(asList(ALICE_USERNAME));

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Charlie -> I'm in New York today! Anyone want to have a coffee?");
        socialNetwork.messageParser("Charlie follows Alice");
        socialNetwork.messageParser("Charlie wall");

        verify(consoleMock).print("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)\n");
        verify(consoleMock).print(ALICE_POST_MESSAGE_WITH_TIME_STAMP_OF_FIVE_MINUTES);
    }

}
