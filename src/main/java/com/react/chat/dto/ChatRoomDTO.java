package com.react.chat.dto;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private String roomName;
    private List<ChatMessage> chatMessageList;
}
