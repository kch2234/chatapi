package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SpringBootTest
@Slf4j
@Transactional // EntityManager 사용위해
@Rollback(value = false) // 테스트에서는 자동롤백이 되므로, DB에 수정결과 유지하기 위해 롤백안되게 설정
class ChatRoomRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    @Test
    public void testInsert() {
        for (int i = 1; i <= 10; i++) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomName("room" + i)
                    .build();
            chatRoomRepository.save(chatRoom); // 저장
        }// for
    }

    // 회원 id로 채팅방 목록 조회
    @Test
    public void listTest() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        log.info("chatRooms : {}", chatRooms);
    }
}