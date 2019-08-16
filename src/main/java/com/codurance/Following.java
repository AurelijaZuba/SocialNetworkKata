package com.codurance;

import java.util.Objects;

public class Following {
    private String follower;
    private String followingUser;

    public Following(String follower, String followingUser) {
        this.follower = follower;
        this.followingUser = followingUser;
    }


    public String getFollowingUser() {
        return followingUser;
    }

    public String getFollower() {
        return follower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Following following = (Following) o;
        return Objects.equals(follower, following.follower) &&
                Objects.equals(followingUser, following.followingUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followingUser);
    }

    @Override
    public String toString() {
        return "Following{" +
                "follower='" + follower + '\'' +
                ", followingUser='" + followingUser + '\'' +
                '}';
    }
}
