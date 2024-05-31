package com.react.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomDTO {
    private Long id;
    private String roomName;
    // TODO : 테스트 후 String -> Member로 변경
    private String member;
    private String toMember;

    private List<ChatMessageDTO> chatMessageList;

}
