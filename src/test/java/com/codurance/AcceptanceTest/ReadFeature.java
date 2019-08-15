package com.codurance.AcceptanceTest;

import com.codurance.LocalClock;
import com.codurance.Repository;
import com.codurance.SocialConsole;
import com.codurance.SocialNetwork;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ReadFeature {

    @Test
    void AT_allow_users_to_read_their_feed() {
        SocialConsole console = mock(SocialConsole.class);
        Repository repository = new Repository();
        LocalClock clock = mock(LocalClock.class);

        given(clock.now()).willReturn(LocalDateTime.now());

        SocialNetwork socialNetwork = new SocialNetwork(repository, console, clock);

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Bob -> Good game though.");
        socialNetwork.messageParser("Alice");

        verify(console).print("I love the weather today (5 minutes ago)");
        verify(console, never()).print("Damn! We lost! (2 minutes ago)");
        verify(console, never()).print("Good game though. (1 minutes ago)");

        socialNetwork.messageParser("Bob");
        verify(console).print("Damn! We lost! (2 minutes ago)");
        verify(console).print("Good game though. (1 minutes ago)");
        verify(console, never()).print("I love the weather today (5 minutes ago)");

    }
}
