package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    void updateLastMessage(Long id, LocalDateTime timestamp, String content);
}
