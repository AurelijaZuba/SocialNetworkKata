package com.codurance;

import java.util.List;

public class SocialNetwork {
    private Repository repository;
    private SocialConsole console;
    private LocalClock clock;

    public SocialNetwork(Repository repository, SocialConsole console, LocalClock clock) {
        this.repository = repository;
        this.console = console;
        this.clock = clock;
    }

    public void messageParser(String message) {
        String[] word = message.split(" ");

        if (word.length == 1) {
            read(message);
        } else if (word.length == 2) {
            wall(message);
        } else {
            post(message);
        }
    }

    private void read(String username) {
        List<Message> allMessages = repository.getMessages(username);
        for (Message m : allMessages) {
            console.print(m.getMessage() + clock.now());
        }
    }

    private void post(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        repository.addMessage(new Message(username, postMessage, clock.now()));
    }

    private void wall(String message) {
        String[] splitMessage = message.split(" ");
        final String username = splitMessage[0];

        List<Message> allMessages = repository.getMessages(username);
        for (Message m : allMessages) {
            console.print(m.getUsername() + " - " + m.getMessage());
        }
    }
}
