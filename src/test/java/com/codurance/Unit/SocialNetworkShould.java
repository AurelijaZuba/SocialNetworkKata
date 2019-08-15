package com.codurance.Unit;

import com.codurance.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SocialNetworkShould {

    private final LocalDateTime dateTime = LocalDateTime.of(2019, Month.AUGUST, 14, 15, 19);
    private static final String ALICE_POST_MESSAGE = "Alice -> I love the weather today";
    private static final String BOB_POST_MESSAGE = "Bob -> Damn! We lost!";
    private static final String ALICE_WALL = "Alice wall";
    private static final String BOB_WALL = "Bob wall";

    private Repository repositoryMock;
    private SocialConsole consoleMock;
    private LocalClock clockMock;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(Repository.class);
        consoleMock = mock(SocialConsole.class);
        userRepositoryMock = mock(UserRepository.class);

        clockMock = mock(LocalClock.class);
        when(clockMock.now()).thenReturn(dateTime);
    }

    @Test
    void add_new_user_when_post_first_message() {
        SocialNetwork socialNetwork = new SocialNetwork(repositoryMock, consoleMock, clockMock, userRepositoryMock);
        socialNetwork.messageParser(ALICE_POST_MESSAGE);

        verify(userRepositoryMock).addNewUser(new User("Alice"));
    }

    @Test
    void post_single_message_for_one_user() {
        SocialNetwork socialNetwork = new SocialNetwork(repositoryMock, consoleMock, clockMock, userRepositoryMock);
        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        given(clockMock.now()).willReturn(dateTime);

        verify(repositoryMock).addMessage(new Message("Alice", "I love the weather today", clockMock.now()));
    }

    @Test
    void return_one_message_from_users_wall() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock, clockMock, userRepositoryMock);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);

        verify(consoleMock).print("Alice - I love the weather today");
    }

    @Test
    void return_message_for_the_user_requesting_to_that_wall() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock, clockMock, userRepositoryMock);

        socialNetwork.messageParser(BOB_POST_MESSAGE);
        socialNetwork.messageParser(BOB_WALL);
        verify(consoleMock).print("Bob - Damn! We lost!");

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);
        verify(consoleMock).print("Alice - I love the weather today");
    }

    @Test
    void return_one_message_with_time_stamp() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(5);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice");
        verify(consoleMock).print("I love the weather today (5 minutes ago)");
    }

    @Test
    void return_two_messages_with_time_stamp() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock, clockMock, userRepositoryMock);

        given(clockMock.calculateTimeDifference(clockMock.now())).willReturn(5, 4);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser("Alice -> It's raining!");
        socialNetwork.messageParser("Alice");

        verify(consoleMock).print("I love the weather today (5 minutes ago)");
        verify(consoleMock).print("It's raining! (4 minutes ago)");
    }

    @Test
    void allow_one_user_to_follow_another() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock, clockMock, userRepositoryMock);

        socialNetwork.messageParser("Alice follows Bob");

        verify(userRepositoryMock).follow("Alice", "Bob");
    }
}
