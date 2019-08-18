package com.codurance.Unit;

import com.codurance.model.Message;
import com.codurance.repository.MessageRepository;
import com.codurance.service.LocalClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageRepositoryShould {
    private final LocalDateTime dateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 12, 19);

    LocalClock clockMock;

    @BeforeEach
    void setUp() {
        clockMock = mock(LocalClock.class);
        when(clockMock.now()).thenReturn(dateTime);
    }

    @Test
    void add_message_to_the_list() {
        MessageRepository messageRepository = new MessageRepository();

        messageRepository.addMessage(new Message("Alice", "I love the weather today", dateTime));

        List<Message> actualMessage = messageRepository.getMessages("Alice");
        final List<Message> expected = Collections.singletonList(new Message("Alice", "I love the weather today", dateTime));
        assertThat(actualMessage).isEqualTo(expected);
    }
}
