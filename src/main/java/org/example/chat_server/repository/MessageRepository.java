package org.example.chat_server.repository;

import org.example.chat_server.entity.Message;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {

    @Tailable
    @Query("{ roomId: ?0 }")
    public Flux<Message> findByRoomId(String roomId);
}
