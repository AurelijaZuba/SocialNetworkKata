package com.codurance.command;

import com.codurance.model.Message;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;

public class PostCommand implements Command{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final LocalClock clock;
    private final SocialConsole consoleMock;

    public PostCommand(MessageRepository messageRepository, UserRepository userRepository, LocalClock clockMock, SocialConsole consoleMock) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clockMock;
        this.consoleMock = consoleMock;
    }

    @Override
    public void excecute(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        messageRepository.addMessage(new Message(username, postMessage, clock.now()));
    }
}
