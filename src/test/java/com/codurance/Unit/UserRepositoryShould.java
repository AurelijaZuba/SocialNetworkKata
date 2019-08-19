package com.codurance.Unit;

import com.codurance.model.User;
import com.codurance.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryShould {

    private static final String BOB_USERNAME = "Bob";
    private static final String ALICE_USERNAME = "Alice";
    private static final String JAMES_USERNAME = "James";

    @Test
    void add_new_user_to_the_repository() {
        List<User> expectedUser = Collections.singletonList(new User(ALICE_USERNAME));

        UserRepository userRepository = new UserRepository();
        userRepository.addNewUser(new User(ALICE_USERNAME));

        List<User> actualUser = userRepository.getUsers();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void add_two_new_user_to_the_repository() {
        List<User> expectedUser = asList(new User(ALICE_USERNAME), new User(BOB_USERNAME));

        UserRepository userRepository = new UserRepository();
        userRepository.addNewUser(new User(ALICE_USERNAME));
        userRepository.addNewUser(new User(BOB_USERNAME));

        List<User> actualUser = userRepository.getUsers();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void add_users_that_are_being_followed() {
        UserRepository userRepository = new UserRepository();

        userRepository.follow(ALICE_USERNAME, BOB_USERNAME);

        List<String> actualUser = userRepository.getFollowedUsers(new User(ALICE_USERNAME));
        assertThat(actualUser).isEqualTo(asList(BOB_USERNAME));
    }

    @Test
    void return_followed_users_for_a_user_who_followed_them() {
        UserRepository userRepository = new UserRepository();

        userRepository.follow(ALICE_USERNAME, BOB_USERNAME);
        userRepository.follow(ALICE_USERNAME, JAMES_USERNAME);
        userRepository.follow(JAMES_USERNAME, BOB_USERNAME);

        List<String> actualUser = userRepository.getFollowedUsers(new User(ALICE_USERNAME));
        assertThat(actualUser).isEqualTo(asList(BOB_USERNAME, JAMES_USERNAME));

        List<String> actualUser2 = userRepository.getFollowedUsers(new User(JAMES_USERNAME));
        assertThat(actualUser2).isEqualTo(asList(BOB_USERNAME));
    }
}
