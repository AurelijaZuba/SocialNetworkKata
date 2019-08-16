package com.codurance;

import java.util.List;

public class SocialNetwork {
    private MessageRepository messageRepository;
    private SocialConsole console;
    private LocalClock clock;
    private UserRepository userRepository;

    public SocialNetwork(MessageRepository messageRepository, SocialConsole console, LocalClock clock, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.console = console;
        this.clock = clock;
        this.userRepository = userRepository;
    }

    public void messageParser(String message) {
        String[] word = message.split(" ");

        checkUserExist(word[0]);

        messageCommand(message, word);
    }

    public void messageCommand(String message, String[] splitWord) {
        switch (splitWord.length) {
            case 1:
                read(message);
                break;
            case 2:
                wall(message);
                break;
            case 3:
                follow(message);
                break;
            default:
                post(message);
        }
    }

    private void checkUserExist(String username) {
        userRepository.addNewUser(new User(username));
    }

    void follow(String message) {

        String[] splitMessage = message.split(" ");

        final String userFollows = splitMessage[0].trim();
        final String userFollowing = splitMessage[2].trim();

        userRepository.follow(userFollows, userFollowing);
    }

    void read(String username) {
        List<Message> allMessages = messageRepository.getMessages(username);
        for (Message m : allMessages) {
            console.print(m.getMessage() + " (" + clock.calculateTimeDifference(clock.now()) + " minutes ago)");
        }
    }

    void post(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        messageRepository.addMessage(new Message(username, postMessage, clock.now()));
    }

    void wall(String message) {
        String[] splitMessage = message.split(" ");
        final String username = splitMessage[0];

        List<Message> allMessages = messageRepository.getMessages(username);
        for (Message m : allMessages) {
            console.print(m.getUsername() + " - " + m.getMessage());
        }
    }
}
