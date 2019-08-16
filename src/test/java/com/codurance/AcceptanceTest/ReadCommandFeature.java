package com.codurance.AcceptanceTest;

import com.codurance.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ReadCommandFeature {

    @Test
    void AT_allow_users_to_read_their_feed() {
        SocialConsole console = mock(SocialConsole.class);
        LocalClock clock = mock(LocalClock.class);
        MessageRepository messageRepository = new MessageRepository();
        UserRepository userRepository = new UserRepository();

        when(clock.now()).thenReturn(LocalDateTime.of(2019, Month.AUGUST, 14, 15, 19));

        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, console, clock, userRepository);

        given(clock.calculateTimeDifference(clock.now())).willReturn(5);
        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Bob -> Good game though.");
        socialNetwork.messageParser("Alice");

        verify(console).print("I love the weather today (5 minutes ago)");
        verify(console, never()).print("Damn! We lost! (2 minutes ago)");
        verify(console, never()).print("Good game though. (1 minutes ago)");

        given(clock.calculateTimeDifference(clock.now())).willReturn(2, 1);
        socialNetwork.messageParser("Bob");
        verify(console).print("Damn! We lost! (2 minutes ago)");
        verify(console).print("Good game though. (1 minutes ago)");
    }
}