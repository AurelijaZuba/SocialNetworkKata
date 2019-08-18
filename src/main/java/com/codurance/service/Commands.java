package com.codurance.service;

import com.codurance.model.Message;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class Commands implements Formatter {

    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private LocalClock clock;
    private SocialConsole console;

    public Commands(MessageRepository messageRepository, UserRepository userRepository, LocalClock clockMock, SocialConsole consoleMock) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clockMock;
        this.console = consoleMock;
    }


    public void follow(String message) {
        String[] splitMessage = message.split(" ");
        final String userFollows = splitMessage[0].trim();
        final String userFollowing = splitMessage[2].trim();

        userRepository.follow(userFollows, userFollowing);
    }

    public void post(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        messageRepository.addMessage(new Message(username, postMessage, clock.now()));
    }

    public void wallRead(String post, String command) {
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
        return message.getMessage() + timeDifference(message.getTime());
    }

    private String timeDifference(LocalDateTime time) {
        var timeDifference = clock.calculateTimeDifference(time);
        if (timeDifference < 60) {
            return " (" + timeDifference + " seconds ago)";
        }
        return " (" + (timeDifference / 60) + " minutes ago)";

    }
}