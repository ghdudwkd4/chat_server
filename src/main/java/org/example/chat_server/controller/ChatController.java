package org.example.chat_server.controller;

import lombok.RequiredArgsConstructor;
import org.example.chat_server.model.Message;
import org.example.chat_server.service.ChatService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Mono<Message> postMessage(@RequestBody Message message) {
        return chatService.saveMessage(message);
    }

    @GetMapping
    public Flux<Message> getMessages() {
        return chatService.getAllMessages();
    }
}
