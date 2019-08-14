package com.codurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SocialNetworkShould {

    public static final String ALICE_POST_MESSAGE = "Alice -> I love the weather today";
    public static final String BOB_POST_MESSAGE = "Bob -> Damn! We lost!";
    public static final String ALICE_WALL = "Alice wall";
    public static final String BOB_WALL = "Bob wall";

    private Repository repositoryMock;
    private SocialConsole consoleMock;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(Repository.class);
        consoleMock = mock(SocialConsole.class);
    }

    @Test
    void post_single_message_for_one_user() {
        SocialNetwork socialNetwork = new SocialNetwork(repositoryMock, consoleMock);
        socialNetwork.messageParser(ALICE_POST_MESSAGE);

        verify(repositoryMock).addMessage("Alice", "I love the weather today");
    }

    @Test
    void return_one_message_from_users_wall() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock);

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);

        verify(consoleMock).print("Alice - I love the weather today");
    }

    @Test
    void return_message_for_the_user_requesting_to_that_wall() {
        Repository repository = new Repository();
        SocialNetwork socialNetwork = new SocialNetwork(repository, consoleMock);

        socialNetwork.messageParser(BOB_POST_MESSAGE);
        socialNetwork.messageParser(BOB_WALL);
        verify(consoleMock).print("Bob - Damn! We lost!");

        socialNetwork.messageParser(ALICE_POST_MESSAGE);
        socialNetwork.messageParser(ALICE_WALL);
        verify(consoleMock).print("Alice - I love the weather today");
    }
}
