package org.example.chat_server.repository;

import org.example.chat_server.entity.Room;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoomRepository extends ReactiveMongoRepository<Room, String> {
    public Flux<Room> findByRegIdAndUseYn(String regId, String useYn);
}
