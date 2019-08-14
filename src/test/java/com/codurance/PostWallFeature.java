package com.codurance;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PostWallFeature {

    @Test
    void AcceptanceTest() {
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