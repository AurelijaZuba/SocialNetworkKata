package com.codurance.command;

import com.codurance.model.Message;
import com.codurance.repository.MessageRepository;
import com.codurance.service.LocalClock;

public class Post implements Command {

    private final MessageRepository messageRepository;
    private final LocalClock clock;

    public Post(MessageRepository messageRepository, LocalClock clock) {
        this.messageRepository = messageRepository;
        this.clock = clock;
    }

    @Override
    public void execute(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        messageRepository.addMessage(new Message(username, postMessage, clock.now()));
    }
}
