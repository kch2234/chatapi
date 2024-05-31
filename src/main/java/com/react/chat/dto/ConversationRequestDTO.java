package com.react.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationRequestDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private boolean acceptFlag;
}
