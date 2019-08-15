package com.codurance.AcceptanceTest;

import com.codurance.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PostWallCommandFeatures {

    @Test
    void AT_post_message_and_see_it_on_users_wall() {
        SocialConsole console = mock(SocialConsole.class);
        LocalClock clock = mock(LocalClock.class);
        Repository repository = new Repository();
        UserRepository userRepository = new UserRepository();

        SocialNetwork socialNetwork = new SocialNetwork(repository, console, clock, userRepository);

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Alice wall");

        verify(console).print("Alice - I love the weather today");
        verify(console, never()).print("Bob - Damn! We lost!");
    }
}
