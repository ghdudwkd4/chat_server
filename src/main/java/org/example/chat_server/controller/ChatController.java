package org.example.chat_server.controller;

import lombok.RequiredArgsConstructor;
import org.example.chat_server.entity.RoomUser;
import org.example.chat_server.entity.Message;
import org.example.chat_server.entity.Room;
import org.example.chat_server.repository.MessageRepository;
import org.example.chat_server.repository.RoomRepository;
import org.example.chat_server.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final RoomRepository roomRepo;
    private final MessageRepository messageRepo;

    /**
     * @param room
     * @Description : 채팅방 생성
     * **/
    @PostMapping("/room/set")
    public Mono<Room> createRoom(@RequestBody Room room) {
        //TODO 단체방 기능, 채팅방 나가기, 채팅방 명 수정 기능  추가 필요
        return roomRepo.save(room);
    }

    /**
     * @param param
     * @Description : 로그인 한 회원 채팅 방 조회
     * **/
    @PostMapping("/room/all")
    public Flux<RoomUser> getRoomAllByUserIdArr(@RequestBody RoomUser param) {
        try {
            return chatService.getRoomAllByUserIdArr( param.getUserIdArr() );
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.empty();
        }
    }

    /**
     * @param param
     * @Description : 방이 있는지 확인 후 없으면 생성
     * **/
    @PostMapping("/room/check")
    public Mono<RoomUser> getRoomCheck( @RequestBody RoomUser param ) {
        try {
            return chatService.getRoomCheck( param );
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.empty();
        }
    }

    /**
     * @param param
     * @Description : 메세지 전송
     * **/
    @PostMapping("/message/set")
    public Mono<Message> sendMsg(@RequestBody Message param) {
        param.setRegDt(LocalDateTime.now());
        return messageRepo.save(param);
    }

    /**
     * @param roomId
     * @Description : 실시간 메세지 조회 (SSE)
     * **/
    @GetMapping(value = "/room/{roomId}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getMessages( @PathVariable String roomId ) {
        try {
            return chatService.streamMessages(roomId);
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.empty();
        }
    }
}
