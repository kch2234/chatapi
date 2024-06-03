package com.react.chat.controller;

import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/")
    public Map<String, Long> add(@RequestBody ChatRoomDTO chatRoomDTO) {
        log.info("chatRoomDTO : {}", chatRoomDTO);
        Long savedId = chatService.add(chatRoomDTO);
        return Map.of("roomId", savedId);
    }

    // 채팅방 메세지 조회
    @GetMapping("/{roomId}/messages")
    public ChatRoomDTO get(@PathVariable("roomId") Long roomId) {
        log.info("roomId : {}", roomId);
        ChatRoomDTO roomDTO = chatService.get(roomId);
        return roomDTO;
    }

    // 채팅방 메세지 전송

    // 채팅방 목록 조회

}
