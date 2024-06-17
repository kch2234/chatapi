package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 사용자가 속한 채팅방 목록 조회
    @Query("select cr from ChatRoom cr join cr.members m where m.id = :memberId")
    List<ChatRoom> findByMembersEmail(Long memberId);
}
