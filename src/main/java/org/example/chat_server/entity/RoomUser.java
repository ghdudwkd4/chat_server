package org.example.chat_server.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.chat_server.com.model.BaseParam;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "roomUser")
public class RoomUser extends BaseParam {

    @Id
    private String id;
    // 방 이름
    private String roomNm;
    // 방 ID
    private String roomId;
    // 유저 ID
    private List<String> userIdArr;

}