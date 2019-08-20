package com.codurance.service;

import com.codurance.command.Read;
import com.codurance.command.Wall;
import com.codurance.model.Message;

import java.time.LocalDateTime;

public class Formatter {
    private Wall wall;
    private Read read;
    private LocalClock clock;

    public Formatter(Wall wall, LocalClock clock) {
        this.wall = wall;
        this.clock = clock;
    }

    public Formatter(Read read, LocalClock clock) {
        this.read = read;
        this.clock = clock;
    }

    public String wallCommand(Message message) {
        return message.getUsername() + " - " + message.getMessage() + timeDifference(message.getTime());
    }

    public String readCommand(Message message) {
        return message.getMessage() + timeDifference(message.getTime());
    }

    private String timeDifference(LocalDateTime time) {
        var timeDifference = clock.calculateTimeDifference(time);
        if (timeDifference < 60) {
            return " (" + timeDifference + " seconds ago)\n";
        }
        return " (" + (timeDifference / 60) + " minutes ago)\n";
    }
}
