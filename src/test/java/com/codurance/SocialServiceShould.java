package com.codurance;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SocialServiceShould {

    @Test
    void post_single_message_for_one_user() {
        Repository repositoryMock = mock(Repository.class);

        SocialNetwork socialNetwork = new SocialNetwork(repositoryMock);
        socialNetwork.messageParser("Alice -> I love the weather today");

        verify(repositoryMock).addMessage("Alice", "I love the weather today");
    }

}
