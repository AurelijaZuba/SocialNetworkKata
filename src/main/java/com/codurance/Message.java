package com.codurance;

public class Message {
    private final String username;
    private final String message;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
