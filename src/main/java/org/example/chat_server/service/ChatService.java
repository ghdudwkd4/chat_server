package org.example.chat_server.service;

import lombok.RequiredArgsConstructor;

import org.example.chat_server.entity.Message;
import org.example.chat_server.entity.RoomUser;
import org.example.chat_server.repository.MessageRepository;
import org.example.chat_server.repository.RoomUserRepository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepo;
    private final RoomUserRepository roomUserRepo;
    private final MongoTemplate mongoTemplate;


    /**
     * @param userIdArr
     * @Description : 로그인 한 회원 채팅방 조회
     * **/
    public Flux<RoomUser> getRoomAllByUserIdArr(List<String> userIdArr) {
        return roomUserRepo.findAllByUserIdArr(userIdArr);
    }

    /**
     * @param param
     * @Description : 방 있는 지 체크
     * **/
    public Mono<RoomUser> getRoomCheck(RoomUser param) throws NoSuchAlgorithmException {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
        Map<String , Object> map = (Map<String , Object>) obj;

        // 채팅방 유저 배열로 변경
        /*
        List<String> list = new ArrayList<>();
        list.add(param.getUserId());
        list.add(map.get("userId").toString());
        */

        // 사용자 ID 정렬
        Collections.sort(param.getUserIdArr());
        // 채팅방 ID 세팅
        param.setRoomId(encodeRoomId(param.getUserIdArr()));

        // 사용자 ID 세팅
        /*
        Collections.sort(list);
        param.setUserId(String.join("-", list));
        */

        // 등록자/등록일 세팅
        param.setRegId(map.get("userId").toString());
        param.setRegDt(LocalDateTime.now());

        return roomUserRepo.findByRoomId(param.getRoomId())
                .switchIfEmpty(roomUserRepo.save(param));
    }

    /**
     * @param roomId
     * @Description : 메세지 조회
     * **/
    public Flux<Message> streamMessages(String roomId) {
        return messageRepo.findByRoomId(roomId)
                .subscribeOn(Schedulers.boundedElastic());
    }


    /**
     * @param userIds
     * @Description : 방 ID 생성 (모듈)
     * **/
    public String encodeRoomId(List<String> userIds) throws NoSuchAlgorithmException {
        // 사용자 ID 정렬
        Collections.sort(userIds);
        // 결합된 ID 문자열 생성
        String combinedIds = String.join("-", userIds);

        // SHA-256 해시 계산
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(combinedIds.getBytes(StandardCharsets.UTF_8));

        // 해시 값을 16진수 문자열로 변환
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
