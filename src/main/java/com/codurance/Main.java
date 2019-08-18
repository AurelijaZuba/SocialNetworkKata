package com.codurance;

import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;
import com.codurance.service.SocialNetwork;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SocialConsole console = new SocialConsole();
        MessageRepository messageRepository = new MessageRepository();
        LocalClock clock = new LocalClock();
        UserRepository userRepositoryMock = new UserRepository();

        SocialNetwork socialNetwork = new SocialNetwork(messageRepository, console, clock, userRepositoryMock);
        Scanner input = new Scanner(System.in);

        System.out.print("Welcome to Social Network! \n");

        while (!"exit".equals(input)) {
            socialNetwork.messageParser(input.nextLine());
        }
        System.out.print("Goodbye");
        input.close();
    }
}
