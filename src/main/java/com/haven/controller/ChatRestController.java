package com.haven.controller;

import com.haven.model.Message;
import com.haven.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/{matchId}")
    public List<Message> getMessages(@PathVariable Long matchId) {

        return chatService.getMessages(matchId);
    }
}