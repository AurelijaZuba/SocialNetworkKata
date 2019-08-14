package com.codurance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class SocialServiceShould {

    @Test
    void post_single_message_for_one_user() {
        Repository repositoryMock = mock(Repository.class);
        SocialConsole consoleMock = mock(SocialConsole.class);

        SocialNetwork socialNetwork = new SocialNetwork(repositoryMock, consoleMock);
        socialNetwork.messageParser("Alice -> I love the weather today");

        verify(repositoryMock).addMessage("Alice", "I love the weather today");
    }

    @Test
    void return_one_message_from_users_wall() {
        SocialConsole consoleMock = mock(SocialConsole.class);
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock);

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Alice wall");

        verify(consoleMock).print("Alice - I love the weather today");
    }

    @Test
    void return_message_for_the_user_requesting_to_that_wall() {
        SocialConsole consoleMock = mock(SocialConsole.class);
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock);

        socialNetwork.messageParser("Bob -> Damn! We lost!");
        socialNetwork.messageParser("Bob wall");
        verify(consoleMock).print("Bob - Damn! We lost!");

        socialNetwork.messageParser("Alice -> I love the weather today");
        socialNetwork.messageParser("Alice wall");
        verify(consoleMock).print("Alice - I love the weather today");
    }


}
