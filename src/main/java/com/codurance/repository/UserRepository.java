package com.codurance.repository;

import com.codurance.model.Following;
import com.codurance.model.User;

import java.util.ArrayList;
import java.util.List;

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
        String user = username.getUsername();

        List<String> followedUsers = new ArrayList<>();
        for (Following following : followUsers) {
            if (isFollower(user, following)) {
                followedUsers.add(following.getFollowingUser());
            }
        }
        return followedUsers;
    }

    private boolean isFollower(String user, Following following) {
        return following.getFollower().equals(user);
    }
}
