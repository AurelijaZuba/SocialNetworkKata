package com.codurance;

import java.util.List;

public class SocialNetwork {
    private Repository repository;
    private SocialConsole console;

    public SocialNetwork(Repository repository, SocialConsole console) {
        this.repository = repository;
        this.console = console;
    }

    public void messageParser(String message) {
        String[] split = message.split(" ");

        if(split[1].equals("wall")){
            wall(message);
        }
        if(split[1].equals("->")) {
            post(message);
        }
    }

    private void post(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        repository.addMessage(username, postMessage);
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
