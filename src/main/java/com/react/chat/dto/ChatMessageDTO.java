package com.react.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageDTO {
    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ChatMessageDTO(Long id, Long chatRoomId, Long senderId, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
