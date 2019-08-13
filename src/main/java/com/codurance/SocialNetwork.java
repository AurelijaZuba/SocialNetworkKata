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

        if(message.contains("wall")){
            String[] splitMessage = message.split(" ");
            final String username = splitMessage[0];

            List<Message> allMessages = repository.getMessages(username);
            System.out.println(allMessages);
            for (Message m : allMessages) {
                console.print(m.getUsername() + " - " + m.getMessage());
            }

        }

        if(message.contains("->")) {
            String[] splitMessage = message.split("->");

            final String username = splitMessage[0].trim();
            final String postMessage = splitMessage[1].trim();
            repository.addMessage(username, postMessage);
        }
    }
}
