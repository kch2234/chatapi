package com.react.chat.service;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatRoomDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class ChatServiceTests {

    @Autowired
    private ChatService chatService;

    // 채팅방 생성 테스트
    @Test
    public void testAdd() {
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .roomName("테스트 채팅방")
                .member(Member.builder().id(5L).build())
                .toMember(Member.builder().id(1L).build())
                .chatMessageList(List.of(ChatMessage.builder().message("테스트 메시지").build()))
                .build();
        log.info("chatRoomDTO : {}", chatRoomDTO);
        // 채팅방 생성
//        Long savedId = chatService.add(chatRoomDTO);
//        log.info("savedId : {}", savedId);
    }

    // 채팅방 조회 테스트
    @Test
    public void getTest() {
        ChatRoomDTO chatRoomDTO = chatService.get(13L);
        log.info("find dto : {}", chatRoomDTO);
    }

    // 채팅방 수정 테스트

    // 채팅방 삭제 테스트

    // 채팅방 목록조회 테스트
    @Test
    public void listTest() {
        List<ChatRoomDTO> list = chatService.getListASC();
        log.info("list : {}", list);
    }

    // 채팅방 메시지 전송 테스트

    // 채팅방 메시지 조회 테스트

    // 채팅방 메시지 수정 테스트

    // 채팅방 메시지 삭제 테스트

    // 채팅방 메시지 목록조회 테스트
}