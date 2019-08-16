package com.codurance.Unit;

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

    @Test
    void return_followed_users_for_a_user_who_followed_them() {
        UserRepository userRepository = new UserRepository();

        userRepository.follow("Alice", "Bob");
        userRepository.follow("Alice", "James");
        userRepository.follow("James", "Bob");

        List<String> actualUser = userRepository.getFollowedUsers(new User("Alice"));
        assertThat(actualUser).isEqualTo(asList("Bob", "James"));

        List<String> actualUser2 = userRepository.getFollowedUsers(new User("James"));
        assertThat(actualUser2).isEqualTo(asList("Bob"));
    }

}
