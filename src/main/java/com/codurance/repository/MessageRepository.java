package com.codurance.repository;

import com.codurance.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {
    private List<Message> allMessages = new ArrayList<>();

    public List<Message> getMessages(String username) {
        List<Message> postedMessages = new ArrayList<>();

        for (Message message : allMessages) {
            if (isUsername(username, message))
                postedMessages.add(new Message(message.getUsername(), message.getMessage(), message.getTime()));
        }

        return postedMessages;
    }

    private boolean isUsername(String username, Message message) {

        return message.getUsername().equals(username);
    }

    public void addMessage(Message message) {
        allMessages.add(new Message(message.getUsername(), message.getMessage(), message.getTime()));
    }
}
