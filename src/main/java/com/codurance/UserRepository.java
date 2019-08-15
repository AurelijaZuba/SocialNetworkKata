package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addNewUser(User username) {
        if (!users.contains(username))
            users.add(username);

    }

    public List<User> getUsers() {
        return users;
    }

    public void follow(String follower, String followingUser) {
        throw new UnsupportedOperationException();
    }

}
