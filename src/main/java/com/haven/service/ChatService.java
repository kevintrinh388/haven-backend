package com.haven.service;

import com.haven.dto.ChatMessage;
import com.haven.model.Message;
import com.haven.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;

    public Message saveMessage(ChatMessage chatMessage) {

        Message message = Message.builder()
                .matchId(chatMessage.getMatchId())
                .sender(chatMessage.getSender())
                .content(chatMessage.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getMessages(Long matchId) {

        return messageRepository.findByMatchIdOrderByTimestampAsc(matchId);
    }
}