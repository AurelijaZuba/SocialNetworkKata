package com.codurance;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SocialConsole console = new SocialConsole();
        MessageRepository messageRepository = new MessageRepository();
        LocalClock clock = new LocalClock();
        UserRepository userRepositoryMock = new UserRepository();

        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, console, clock, userRepositoryMock);
        Scanner input = new Scanner(System.in);

        System.out.print("Welcome to Social Network!");
        String command = input.nextLine();

        socialNetwork.messageParser(command);
    }
}
