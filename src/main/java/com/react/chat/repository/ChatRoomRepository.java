package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
