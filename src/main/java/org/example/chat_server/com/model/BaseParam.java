package org.example.chat_server.com.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseParam {
    private String regId;
    private LocalDateTime regDt;
    private String mdfId;
    private LocalDateTime mdfDt;
}
