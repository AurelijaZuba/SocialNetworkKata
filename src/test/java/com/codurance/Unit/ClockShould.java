package com.codurance.Unit;

import com.codurance.service.LocalClock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockShould {

    @Test
    void check_for_datetime() {
        LocalClock clock = new LocalClock();

        LocalDateTime actual = clock.now();
        assertThat(actual).isNotNull();
    }

    @Test
    void check_for_time_difference() {
        LocalClock clock = new LocalClock();

        LocalDateTime d = LocalDateTime.of(2019, Month.AUGUST, 18, 15, 21, 00);

        long actual = clock.calculateTimeDifference(d);
        System.out.println(actual);
        assertThat(actual).isEqualTo(568);
    }
}
