package com.codurance;

import java.util.List;

public class SocialNetwork {
    private Repository repository;
    private SocialConsole console;
    private LocalClock clock;
    private UserRepository userRepository;

    public SocialNetwork(Repository repository, SocialConsole console, LocalClock clock, UserRepository userRepository) {
        this.repository = repository;
        this.console = console;
        this.clock = clock;
        this.userRepository = userRepository;
    }

    public void messageParser(String message) {
        String[] word = message.split(" ");
        checkUserExist(word[0]);

        if (word.length == 1) {
            read(message);
        }
        if (word.length == 2) {
            wall(message);
        }
        if (word.length == 3) {
            follow(message);
        }
        if (word.length >= 4) {
            post(message);
        }

    }

    private void checkUserExist(String username) {
        userRepository.addNewUser(new User(username));
    }

    private void follow(String message) {
        String[] splitMessage = message.split(" ");

        final String userFollows = splitMessage[0].trim();
        final String userFollowing = splitMessage[2].trim();

        userRepository.follow(userFollows, userFollowing);
    }


    private void read(String username) {
        List<Message> allMessages = repository.getMessages(username);
        for (Message m : allMessages) {
            console.print(m.getMessage() + " (" + clock.calculateTimeDifference(clock.now()) + " minutes ago)");
        }
    }

    private void post(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        repository.addMessage(new Message(username, postMessage, clock.now()));
    }

    private void wall(String message) {
        String[] splitMessage = message.split(" ");
        final String username = splitMessage[0];

        List<Message> allMessages = repository.getMessages(username);
        for (Message m : allMessages) {
            console.print(m.getUsername() + " - " + m.getMessage());
        }
    }
}
