package com.react.chat.controller;

import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatMessageService;
import com.react.chat.service.ChatRoomService;
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

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessageDTO>> list(@PathVariable("chatRoomId") Long chatRoomId) {
        log.info("******** ChatController GET /messages/:chatRoomId - chatRoomId : {}", chatRoomId);
        List<ChatMessageDTO> messageList = chatMessageService.getMessagesByChatRoomId(chatRoomId);
        return ResponseEntity.ok(messageList);
    }

    @PostMapping("/create")
    public ResponseEntity<ChatRoomDTO> createChatRoom(ChatRoomDTO chatRoomDTO) {
        log.info("******** ChatController POST /create - chatRoomDTO : {}", chatRoomDTO);
        ChatRoomDTO createdChatRoom = chatRoomService.createChatRoom(chatRoomDTO);
        return ResponseEntity.ok(createdChatRoom);
    }

    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDTO messageDTO) {
        log.info("Received message - enter: {}", messageDTO);
        chatMessageService.addUser(messageDTO);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO messageDTO) {
        log.info("Received message - message: {}", messageDTO);
        chatMessageService.sendMessage(messageDTO);
    }

    @MessageMapping("/chat/leave")
    public void leave(ChatMessageDTO messageDTO) {
        log.info("Received message - leave: {}", messageDTO);
        chatMessageService.leaveUser(messageDTO);
    }
}
