package com.codurance.command;

import com.codurance.model.Message;
import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.Formatter;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;

import java.util.List;

public class Wall implements Command {
    private final String WALL_COMMAND = "wall";

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SocialConsole console;
    private final Formatter formatter;

    public Wall(MessageRepository messageRepository, UserRepository userRepository, SocialConsole console, LocalClock clock) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.console = console;
        formatter = new Formatter(this, clock);
    }

    @Override
    public void execute(String message) {
        String[] splitMessage = message.split(" ");
        String username = splitMessage[0];

        List<String> followersList = userRepository.getFollowedUsers(new User(username));

        List<Message> allMessages = messageRepository.getMessages(username);
        getUserMessages(WALL_COMMAND, allMessages);

        hasFollowers(WALL_COMMAND, followersList);
    }

    private void hasFollowers(String command, List<String> followersList) {
        for (var followerMessages : followersList) {
            List<Message> allMessages = messageRepository.getMessages(String.valueOf(followerMessages));

            getUserMessages(command, allMessages);
        }
    }

    private void getUserMessages(String command, List<Message> allMessages) {
        for (Message message : allMessages) {
            isCommand(command, message);
        }
    }

    private void isCommand(String command, Message message) {
        if (command.equals(WALL_COMMAND)) {
            console.print(formatter.wallCommand(message));
        }
    }
}
