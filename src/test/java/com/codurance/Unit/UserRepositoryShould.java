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

}
