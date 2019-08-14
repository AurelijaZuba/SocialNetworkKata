package com.codurance.AcceptanceTest;

import com.codurance.Repository;
import com.codurance.SocialConsole;
import com.codurance.SocialNetwork;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PostWallFeature {

    @Test
    void AT_post_message_and_see_it_on_users_wall() {
        SocialConsole console = mock(SocialConsole.class);
        Repository repository = new Repository();

        SocialNetwork socialNetwork = new SocialNetwork(repository, console);

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Alice wall");

        verify(console).print("Alice - I love the weather today");
        verify(console, never()).print("Bob - Damn! We lost!");
    }
}
