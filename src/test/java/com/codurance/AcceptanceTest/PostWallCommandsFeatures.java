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

public class PostWallCommandsFeatures {

    @Test
    void AT_post_message_and_see_it_on_users_wall() {
        SocialConsole console = mock(SocialConsole.class);
        LocalClock clock = mock(LocalClock.class);
        MessageRepository messageRepository = new MessageRepository();
        UserRepository userRepository = new UserRepository();
        when(clock.now()).thenReturn(LocalDateTime.of(2019, Month.AUGUST, 14, 15, 19));

        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, userRepository, clock, console);

        given(clock.calculateTimeDifference(clock.now())).willReturn(300);

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Alice wall");

        verify(console).print("Alice - I love the weather today (5 minutes ago)");
        verify(console, never()).print("Bob - Damn! We lost! (2 minutes ago)");
    }
}
