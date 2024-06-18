package com.react.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.enumFiles.MessageType;
import com.react.chat.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private MessageType messageType;
    private String content;
    private Member sender;
//    private String nickname;
    private Long roomId;
    private LocalTime timestamp = LocalTime.now();

    @JsonProperty("type")
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public ChatMessage toEntity(ChatRoom chatRoom) {
        return ChatMessage.builder()
                .id(id)
                .messageType(messageType)
                .content(content)
                .sender(sender)
//                .sender(Member.builder().nickname(nickname).build())
                .chatRoom(chatRoom)
                .timestamp(timestamp)
                .build();
    }
}

