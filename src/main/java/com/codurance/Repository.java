package com.codurance;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    List<Message> messages = new ArrayList<>();

    public void addMessage(String username, String message) {
        messages.add(new Message(username, message));
    }
}
