package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addUser(User username) {
        if(!users.contains(username))
            users.add(username);
    }
}
