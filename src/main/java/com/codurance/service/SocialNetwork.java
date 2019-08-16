package com.codurance.service;

import com.codurance.model.Message;
import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;

import java.util.List;

public class SocialNetwork implements Formatter {
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
                wallReadCommand(message, "read");
                break;
            case 2:
                wallReadCommand(message, "wall");
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

    private void follow(String message) {
        String[] splitMessage = message.split(" ");
        final String userFollows = splitMessage[0].trim();
        final String userFollowing = splitMessage[2].trim();

        userRepository.follow(userFollows, userFollowing);
    }

    private void post(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        messageRepository.addMessage(new Message(username, postMessage, clock.now()));
    }

    private void wallReadCommand(String post, String command) {
        String[] splitMessage = post.split(" ");
        final String username = splitMessage[0];

        List<Message> allMessages = messageRepository.getMessages(username);
        for (Message message : allMessages) {
            isCommand(command, message);
        }
    }

    private void isCommand(String command, Message message) {
        if (command.equals("wall")) {
            console.print(wallFormatter(message));
        }
        console.print(readFormatter(message));
    }


    @Override
    public String wallFormatter(Message message) {
        return message.getUsername() + " - " + message.getMessage();
    }

    @Override
    public String readFormatter(Message message) {
        return message.getMessage() + " (" + clock.calculateTimeDifference(clock.now()) + " minutes ago)";
    }
}
