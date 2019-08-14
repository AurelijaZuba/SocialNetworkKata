package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    List<Message> messages = new ArrayList<>();

    public void addMessage(String username, String message) {
        messages.add(new Message(username, message));
    }

    public List<Message> getMessages(String username) {
        List<Message> result = new ArrayList<>();

        for (Message msg : messages) {
            if (msg.getUsername().equals(username))
                result.add(new Message(msg.getUsername(), msg.getMessage()));
        }

        return result;
    }
}
