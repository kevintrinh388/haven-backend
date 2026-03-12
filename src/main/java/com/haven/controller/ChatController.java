package com.haven.controller;

import com.haven.dto.ChatMessage;
import com.haven.service.ChatService;
import com.haven.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage/{matchId}")
    @SendTo("/topic/messages/{matchId}")
    public Message sendMessage(ChatMessage message) {
        return chatService.saveMessage(message);
    }
}