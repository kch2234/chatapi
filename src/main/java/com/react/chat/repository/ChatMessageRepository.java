package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 채팅방 id로 메시지 목록 조회
    List<ChatMessage> findAllByChatRoomId(Long chatRoomId);
}
