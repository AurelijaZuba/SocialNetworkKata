package com.codurance.command;

import com.codurance.repository.UserRepository;

public class Follow implements Command {
    private UserRepository userRepository;

    public Follow(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public void execute(String message) {
        String[] splitMessage = message.split(" ");
        final String userFollows = splitMessage[0].trim();
        final String userFollowing = splitMessage[2].trim();

        userRepository.follow(userFollows, userFollowing);
    }
}
