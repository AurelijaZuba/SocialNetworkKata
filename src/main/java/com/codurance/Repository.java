package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    public void addMessage(String username, String message) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(username, message));

    }
}
