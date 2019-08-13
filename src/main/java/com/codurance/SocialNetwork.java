package com.codurance;

public class SocialNetwork {
    private Repository repository;

    public SocialNetwork(Repository repository) {
        this.repository = repository;
    }

    public void messageParser(String message) {
        String[] splitMessage = message.split("->");

        final String username = splitMessage[0].trim();
        final String postMessage = splitMessage[1].trim();
        repository.addMessage(username, postMessage);
    }
}
