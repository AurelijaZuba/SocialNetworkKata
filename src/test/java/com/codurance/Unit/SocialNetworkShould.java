package com.codurance.Unit;

import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;
import com.codurance.service.SocialNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.*;

public class SocialNetworkShould {

    private final LocalDateTime dateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 12, 19);
    private static final String ALICE_POST_MESSAGE = "Alice -> I love the weather today";

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
    }


    @Test
    void add_new_user_when_post_first_message() {
        SocialNetwork socialNetwork = new SocialNetwork(messageRepositoryMock, consoleMock, clockMock, userRepositoryMock);
        socialNetwork.messageParser(ALICE_POST_MESSAGE);

        verify(userRepositoryMock).addNewUser(new User("Alice"));
    }
}
