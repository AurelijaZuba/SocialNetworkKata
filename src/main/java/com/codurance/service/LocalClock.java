package com.codurance.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalClock {
    public LocalDateTime now(){
        return LocalDateTime.now();
    }

    public int calculateTimeDifference(LocalDateTime time) {
        long between = ChronoUnit.MINUTES.between(time, now());
        return (int) between;
    }
}
