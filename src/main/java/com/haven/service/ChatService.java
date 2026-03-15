package com.haven.service;

import com.haven.dto.ChatMessage;
import com.haven.enums.MessageStatus;
import com.haven.model.Message;
import com.haven.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
                .status(MessageStatus.SENT)
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getMessages(Long matchId, int limit) {

        Pageable pageable = Pageable.ofSize(limit);
        return messageRepository.findByMatchIdOrderByTimestampAsc(matchId, pageable);
    }

    public void markMessageSeen(Long messageId) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow();

        message.setStatus(MessageStatus.SEEN);

        messageRepository.save(message);
    }

    public void markMessageDelivered(Long messageId) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow();

        message.setStatus(MessageStatus.DELIVERED);

        messageRepository.save(message);
    }
}