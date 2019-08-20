package com.codurance.service;

import com.codurance.command.Follow;
import com.codurance.command.Post;
import com.codurance.command.Read;
import com.codurance.command.Wall;
import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;

public class SocialNetwork {
    private final LocalClock clock;
    private UserRepository userRepository;
    private final SocialConsole console;
    private MessageRepository messageRepository;

    public SocialNetwork(MessageRepository messageRepository, UserRepository userRepository, LocalClock clock, SocialConsole console) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clock;
        this.console = console;
    }

    public void messageParser(String message) {
        String[] word = message.split(" ");

        checkUserExist(word[0]);

        messageCommand(message, word);
    }

    public void messageCommand(String message, String[] splitWord) {
        switch (splitWord.length) {
            case 1:
                new Read(messageRepository, userRepository, console, clock).execute(message);
                break;
            case 2:
                new Wall(messageRepository, userRepository, console, clock).execute(message);
                break;
            case 3:
                new Follow(userRepository).execute(message);
                break;
            default:
                new Post(messageRepository, clock).execute(message);
        }
    }

    private void checkUserExist(String username) {
        userRepository.addNewUser(new User(username));
    }
}
