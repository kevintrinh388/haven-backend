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
    public List<Message> getMessages(
            @PathVariable Long matchId,
            @RequestParam(defaultValue = "50") int limit
    ) {

        return chatService.getMessages(matchId, limit);
    }

    @PutMapping("/{messageId}/seen")
    public void markSeen(@PathVariable Long messageId) {
        chatService.markMessageSeen(messageId);
    }

    @PutMapping("/{messageId}/delivered")
    public void markDelivered(@PathVariable Long messageId) {
        chatService.markMessageDelivered(messageId);
    }
}