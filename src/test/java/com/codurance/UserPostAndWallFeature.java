package com.codurance;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

public class UserPostAndWallFeature {

    @Test
    void AcceptanceTest() {
        SocialService socialService = new SocialService();
        SocialConsole console = new SocialConsole();

        socialService.post("Alice -> I love the weather today");
        socialService.post("Bob -> Damn! We lost!");
        socialService.wall("Alice");


        verify(console).print("Alice - I love the weather today");
    }
}
