package com.codurance.AcceptanceTest;

import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;
import com.codurance.service.SocialNetwork;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class FollowCommandsFeature {

    @Test
    void AT_users_can_follow_other_users() {
        SocialConsole console = mock(SocialConsole.class);
        LocalClock clock = mock(LocalClock.class);
        MessageRepository messageRepository = new MessageRepository();
        UserRepository userRepository = new UserRepository();

        when(clock.now()).thenReturn(LocalDateTime.of(2019, Month.AUGUST, 14, 15, 19));

        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepository, clock, console);

        given(clock.calculateTimeDifference(clock.now())).willReturn(2, 300);

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Charlie -> I'm in New York today! Anyone want to have a coffee?");
        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Charlie follows Alice");
        socialNetwork.messageParser("Charlie wall");

        verify(console).print("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)");
        verify(console).print("Alice - I love the weather today (5 minutes ago)");
        verify(console, never()).print("Bob - Damn! We lost!");
    }
}
