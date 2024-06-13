package com.react.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.enumFiles.MessageType;
import com.react.chat.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private MessageType MessageType;
    private String content;
    private Member sender;
    private ChatRoom ChatRoom;
    private LocalDateTime timestamp;

    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .id(id)
                .messageType(MessageType)
                .content(content)
                .sender(sender)
                .chatRoom(ChatRoom)
                .timestamp(timestamp)
                .build();
    }
}