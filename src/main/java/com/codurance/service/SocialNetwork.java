package com.codurance.service;

import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;

public class SocialNetwork {
    private Commands commands;
    private UserRepository userRepository;

    public SocialNetwork(MessageRepository messageRepository, SocialConsole console, LocalClock clockMock, UserRepository userRepository) {
        this.userRepository = userRepository;
        commands = new Commands(messageRepository, userRepository, clockMock, console);
    }

    public void messageParser(String message) {
        String[] word = message.split(" ");

        checkUserExist(word[0]);

        messageCommand(message, word);
    }

    public void messageCommand(String message, String[] splitWord) {
        switch (splitWord.length) {
            case 1:
                commands.wallRead(message, "read");
                break;
            case 2:
                commands.wallRead(message, "wall");
                break;
            case 3:
                commands.follow(message);
                break;
            default:
                commands.post(message);
        }
    }

    private void checkUserExist(String username) {
        userRepository.addNewUser(new User(username));
    }
}
