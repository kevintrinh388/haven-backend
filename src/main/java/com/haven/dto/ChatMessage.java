package com.haven.dto;

import lombok.*;

@Builder
@Data
public class ChatMessage {

    private Long matchId;
    private String sender;
    private String content;
}