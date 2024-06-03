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
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (i != j) {
                    ChatRoom chatRoom = ChatRoom.builder()
                            .member(Member.builder().id((long) i).build())
                            .toMember(Member.builder().id((long) j).build())
                            .build();
                    chatRoomRepository.save(chatRoom);
                }
            }
        }
    }

    // 회원 id로 채팅방 목록 조회
    @Test
    public void listTest() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMemberId(1L);
        log.info("chatRooms : {}", chatRooms);
    }
}