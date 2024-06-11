package com.react.chat.controller;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageService chatMessageService;

    // 채팅방 메시지 목록 조회
    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessageDTO>> list(@PathVariable("chatRoomId") Long chatRoomId) {
        log.info("******** ChatController GET /messages/:chatRoomId - chatRoomId : {}", chatRoomId);
        List<ChatMessageDTO> messageList = chatMessageService.getMessagesByChatRoomId(chatRoomId);
        return ResponseEntity.ok(messageList);
    }

    // 채팅방 생성
    @PostMapping("/create{id}")
    public ResponseEntity<ChatRoomDTO> createRoom(@PathVariable("id") Long id) {
        log.info("******** ChatController POST /create - id : {}", id);
        ChatRoomDTO chatRoomDTO = chatMessageService.createChatRoom(id);
        return ResponseEntity.ok(chatRoomDTO);
    }


    // 채팅방 입장
    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDTO messageDTO) {
        log.info("Received message - enter: {}", messageDTO);
        chatMessageService.addUser(messageDTO);
    }

    // 채팅방 메시지 전송
    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO messageDTO) {
        log.info("Received message - message: {}", messageDTO);
        chatMessageService.sendMessage(messageDTO);
    }

    // 채팅방 퇴장
    @MessageMapping("/chat/leave")
    public void leave(ChatMessageDTO messageDTO) {
        log.info("Received message - leave: {}", messageDTO);
        chatMessageService.leaveUser(messageDTO);
    }
}
