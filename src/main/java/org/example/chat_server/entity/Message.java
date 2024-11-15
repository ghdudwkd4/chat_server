package org.example.chat_server.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.chat_server.com.model.BaseParam;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "message")
public class Message extends BaseParam {
    @Id
    private String id;
    // 방 ID
    private String roomId;
    // 유저 ID
    private String userId;
    // 닉네임
    private String nickname;
    // 내용
    private String content;
    // 읽은 여부
    private String readYn = "N";
}