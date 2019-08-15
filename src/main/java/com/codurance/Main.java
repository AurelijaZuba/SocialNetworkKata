package com.codurance;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SocialConsole console = new SocialConsole();
        Repository repository = new Repository();
        LocalClock clock = null;
        UserRepository userRepositoryMock = new UserRepository();

        SocialNetwork socialNetwork = new SocialNetwork(repository, console, clock, userRepositoryMock);
        Scanner input = new Scanner(System.in);

        System.out.print("Welcome to Social Network!");
        String command = input.nextLine();

        socialNetwork.messageParser(command);
    }
}
