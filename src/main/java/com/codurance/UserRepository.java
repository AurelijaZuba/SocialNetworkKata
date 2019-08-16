package com.codurance;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class UserRepository {

    private List<User> users = new ArrayList<>();
    private List<Following> followUsers = new ArrayList<>();


    public void addNewUser(User username) {
        if (!users.contains(username))
            users.add(username);

    }

    public List<User> getUsers() {
        return users;
    }

    public void follow(String follower, String followingUser) {
        followUsers.add(new Following(follower, followingUser));
    }

    public List<String> getFollowedUsers(User username) {
        final String user = username.getUsername();

        for (Following following : followUsers) {
            if (following.getFollower().equals(user)) {
                return asList(following.getFollowingUser());
            }
        }
        return asList();
    }
}
