package com.codurance.Unit;

import com.codurance.Following;
import com.codurance.User;
import com.codurance.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryShould {

    @Test
    void add_new_user_to_the_repository() {
        List<User> expectedUser = Collections.singletonList(new User("Alice"));

        UserRepository userRepository = new UserRepository();
        userRepository.addNewUser(new User("Alice"));

        List<User> actualUser = userRepository.getUsers();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void add_two_new_user_to_the_repository() {
        List<User> expectedUser = asList(new User("Alice"), new User("Bob"));

        UserRepository userRepository = new UserRepository();
        userRepository.addNewUser(new User("Alice"));
        userRepository.addNewUser(new User("Bob"));

        List<User> actualUser = userRepository.getUsers();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void add_users_that_are_being_followed() {
        UserRepository userRepository = new UserRepository();

        userRepository.follow("Alice", "Bob");

        List<String> actualUser = userRepository.getFollowedUsers(new User("Alice"));
        assertThat(actualUser).isEqualTo(asList("Bob"));
    }

//    @Test
//    void return_followed_users_for_single_user() {
//        SocialConsole consoleMock = mock(SocialConsole.class);
//        LocalClock clockMock = mock(LocalClock.class);
//        MessageRepository mockMessageRepository = mock(MessageRepository.class);
//        UserRepository userRepository = new UserRepository();
//
//        SocialNetwork socialNetwork = new SocialNetwork(mockMessageRepository, consoleMock, clockMock, userRepository);
//
//        socialNetwork.messageParser("Alice follows Bob");
//
//        List<Following> actualUser = userRepository.getFollowedUsers(new User("Alice"));
//        assertThat(actualUser).isEqualTo(asList("Bob"));
//    }

}