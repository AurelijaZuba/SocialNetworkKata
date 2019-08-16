package com.codurance.Unit;

import com.codurance.service.LocalClock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockShould {

    @Test
    void check_for_datetime() {
        LocalClock clock = new LocalClock();

        LocalDateTime actual = clock.now();
        assertThat(actual).isNotNull();
    }
}
