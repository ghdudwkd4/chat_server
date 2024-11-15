package org.example.chat_server.repository;

import org.example.chat_server.entity.RoomUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface RoomUserRepository extends ReactiveMongoRepository<RoomUser, String> {

    // 채팅 방 체크
    @Query("{userIdArr : { $in: ?0 } }")
    Flux<RoomUser> findAllByUserIdArr(List<String> UserIdArr);

    // 채팅 방 체크
    Mono<RoomUser> findByRoomId(String roomId);
}
