package com.codurance.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private final String username;
    private final String message;
    private LocalDateTime time;

    public Message(String username, String message, LocalDateTime time) {
        this.username = username;
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(username, message1.username) &&
                Objects.equals(message, message1.message) &&
                Objects.equals(time, message1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, message, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
