package com.react.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.react.chat.domain.enumFiles.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private MessageType MessageType;
    private Long chatRoomId;
    // TODO: senderId를 member로 변경
    private Long senderId;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
