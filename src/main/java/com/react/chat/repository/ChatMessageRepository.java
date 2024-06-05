package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
