package com.codurance.command;

import com.codurance.model.Message;
import com.codurance.model.User;
import com.codurance.repository.MessageRepository;
import com.codurance.repository.UserRepository;
import com.codurance.service.Formatter;
import com.codurance.service.LocalClock;
import com.codurance.service.SocialConsole;

import java.util.List;

public class Read implements Command{
    public static final String READ_COMMAND = "read";
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SocialConsole console;
    private final LocalClock clock;
    private final Formatter formatter;

    public Read(MessageRepository messageRepository, UserRepository userRepository, SocialConsole console, LocalClock clock) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.console = console;
        this.clock = clock;
        formatter = new Formatter(this, clock);

    }

    @Override
    public void execute(String message) {
        String[] splitMessage = message.split(" ");
        String username = splitMessage[0];

        List<String> followersList = userRepository.getFollowedUsers(new User(username));

        List<Message> allMessages = messageRepository.getMessages(username);
        getUserMessages(READ_COMMAND, allMessages);

        hasFollowers(READ_COMMAND, followersList);
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
        if (command.equals(READ_COMMAND)) {
            console.print(formatter.readCommand(message));
        }
    }
}
