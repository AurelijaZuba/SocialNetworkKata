package com.codurance.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalClock {

    private int hour;
    private int minute;
    private int second;

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public int calculateTimeDifference(LocalDateTime time) {
        long timeDiff = timeFormatting(time);
        long now = currentTimeFormatting();

        return Math.toIntExact(((int) now - timeDiff));
    }

    private long currentTimeFormatting() {
        var currentTime = now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String[] hourMin = currentTime.split(":");
        hour = Integer.parseInt(hourMin[0]);
        minute = Integer.parseInt(hourMin[1]);
        second = Integer.parseInt(hourMin[2]);

        return (hour * 60) + (minute * 60) + second;
    }

    private long timeFormatting(LocalDateTime time) {
        var timeDiff = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String[] hourMin = timeDiff.split(":");
        hour = Integer.parseInt(hourMin[0]);
        minute = Integer.parseInt(hourMin[1]);
        second = Integer.parseInt(hourMin[2]);
        return (hour * 60) + (minute * 60) + second;

    }
}
