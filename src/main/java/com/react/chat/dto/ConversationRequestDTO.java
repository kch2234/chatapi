package com.react.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConversationRequestDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String status;
    private LocalDateTime createdDate;
}
