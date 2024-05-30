package com.react.chat.service;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ChatRoomServiceTests {

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    public void testAdd() {
    }
}