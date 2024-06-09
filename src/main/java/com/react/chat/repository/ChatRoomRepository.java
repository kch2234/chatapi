package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Modifying
    @Query("UPDATE ChatRoom c SET c.lastMessage = :lastMessage, c.lastMessageTime = :lastMessageTime WHERE c.id = :roomId")
    void updateLastMessage(@Param("roomId") Long roomId, @Param("lastMessageTime") LocalDateTime lastMessageTime, @Param("lastMessage") String lastMessage);
}
