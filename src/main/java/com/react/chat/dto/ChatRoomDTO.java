package com.react.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomDTO {
    private Long id;
    private List<ChatMessageDTO> messageDTOS;

    public ChatRoomDTO(Long id, List<ChatMessageDTO> messageDTOS) {
        this.id = id;
        this.messageDTOS = messageDTOS;
    }
}
