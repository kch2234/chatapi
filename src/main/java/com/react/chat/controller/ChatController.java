package com.react.chat.controller;

import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessageDTO>> list(@PathVariable("chatRoomId") Long chatRoomId) {
        log.info("******** ChatController GET /messages/:chatRoomId - chatRoomId : {}", chatRoomId);
        List<ChatMessageDTO> messageList = chatMessageService.getMessagesByChatRoomId(chatRoomId);
        return ResponseEntity.ok(messageList);
    }

    @PostMapping("/send")
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody ChatMessageDTO messageDTO) {
        log.info("******** ChatController POST /send - messageDTO : {}", messageDTO);
        ChatMessageDTO chatMessage = chatMessageService.sendMessage(messageDTO);
        return ResponseEntity.ok(chatMessage);
    }
}
