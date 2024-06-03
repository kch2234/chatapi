package com.react.chat.controller;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // 채팅방 삭제
    @DeleteMapping("/{roomId}")
    public void delete(@PathVariable("roomId") Long roomId) {
        log.info("Deleting room with id: {}", roomId);
        chatService.delete(roomId);
    }

    // 채팅방 메세지 조회
    @GetMapping("/{roomId}/messages")
    public List<ChatMessageDTO> getMessages(@PathVariable("roomId") Long roomId) {
        log.info("roomId : {}", roomId);
        return chatService.getMessages(roomId);
    }

    // 채팅방 메세지 전송
    @PostMapping("/{roomId}/messages")
    public void sendMessage(@PathVariable("roomId") Long roomId, @RequestBody ChatMessageDTO messageDTO) {
        log.info("Sending message to room {}: {}", roomId, messageDTO);
        chatService.sendMessage(roomId, messageDTO);
    }

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoomDTO> getAllRooms() {
        log.info("Getting all chat rooms");
        return chatService.getAllRooms();
    }

}
