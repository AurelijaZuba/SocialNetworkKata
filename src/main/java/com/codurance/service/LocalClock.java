package com.codurance.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalClock {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public int calculateTimeDifference(LocalDateTime time) {
        var one = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        var two = now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String[] hourMin = one.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int secs = Integer.parseInt(hourMin[2]);

        String[] hourMin1 = two.split(":");
        int hour1 = Integer.parseInt(hourMin1[0]);
        int mins1 = Integer.parseInt(hourMin1[1]);
        int secs1 = Integer.parseInt(hourMin1[2]);

        long difference = (hour * 60 - (hour1 * 60)) + (mins * 60 - (mins1 * 60)) + (secs - secs1);

        return (int) difference;
    }
}
