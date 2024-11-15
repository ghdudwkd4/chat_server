package org.example.chat_server.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.chat_server.com.model.BaseParam;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "room")
public class Room extends BaseParam {

    @Id
    private String id;
    private String roomNm;
    private String useYn;
}