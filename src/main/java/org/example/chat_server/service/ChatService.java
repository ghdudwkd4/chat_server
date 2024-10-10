package org.example.chat_server.service;

import lombok.RequiredArgsConstructor;
import org.example.chat_server.model.Message;
import org.example.chat_server.repository.MessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ChatService {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final MessageRepository messageRepository;

    public Mono<Message> saveMessage(Message message) {
        LocalDateTime timestamp = LocalDateTime.now();
        message.setTimestamp(timestamp.format(TIMESTAMP_FORMATTER));
        return messageRepository.save(message);
    }

    public Flux<Message> getAllMessages() {
        return messageRepository.findAll().flatMap(this::formatMessageTimestamp);
    }

    // 메시지 객체의 타임스탬프를 포맷하는 메서드
    private Mono<Message> formatMessageTimestamp(Message message) {
        LocalDateTime timestamp = LocalDateTime.parse(message.getTimestamp(), TIMESTAMP_FORMATTER);
        LocalDateTime now = LocalDateTime.now();

        if (timestamp.toLocalDate().isEqual(now.toLocalDate())) {
            // 오늘이면 시간만 반환
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            message.setTimestamp(timestamp.format(timeFormatter));
        } else {
            // 오늘이 아니면 전체 타임스탬프 반환
            message.setTimestamp(timestamp.format(TIMESTAMP_FORMATTER));
        }

        return Mono.just(message);
    }
}
