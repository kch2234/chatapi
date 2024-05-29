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

    public ConversationRequestDTO(Long id, Long senderId, Long receiverId, boolean acceptFlag) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.acceptFlag = acceptFlag;
    }
}
