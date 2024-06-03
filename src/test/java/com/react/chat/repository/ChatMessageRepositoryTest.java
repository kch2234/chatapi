package com.react.chat.repository;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional // EntityManager 사용위해
@Rollback(value = false) // 테스트에서는 자동롤백이 되므로, DB에 수정결과 유지하기 위해 롤백안되게 설정
class ChatMessageRepositoryTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // 메시지 등록
    @Test
    public void testInsert() {
        for (int i = 1; i <= 10; i++) {
            ChatMessage chatMessage = ChatMessage.builder()
                    .message("메시지" + i)
                    .chatRoom(ChatRoom.builder().id(1L).build())
                    .member(Member.builder().id(1L).build())
                    .timestamp(LocalDateTime.now())
                    .build();
            chatMessageRepository.save(chatMessage);
        }
    }

    // 메시지 삭제
    @Test
    public void testDelete() {
        Long chatMessageId = 1L;
        chatMessageRepository.deleteById(chatMessageId);
    }

    // 채팅방 id로 메시지 목록 조회
    @Test
    public void testSelect() {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(1L);
        log.info("chatMessages : {}", chatMessages);
    }
}