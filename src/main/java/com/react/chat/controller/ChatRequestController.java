package com.react.chat.controller;

import com.react.chat.dto.ConversationRequestDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ConversationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/requests")
@RequiredArgsConstructor
@Slf4j
public class ChatRequestController {

    private final ConversationRequestService chatRequestService;

    @GetMapping("/{recipientId}")
    public ResponseEntity<List<ConversationRequestDTO>> getPendingRequests(@PathVariable Long recipientId) {
        List<ConversationRequestDTO> requests = chatRequestService.getPendingRequests(recipientId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/request")
    public ResponseEntity<ConversationRequestDTO> requestChat(@RequestParam Long requesterId, @RequestParam Long recipientId) {
        ConversationRequestDTO chatRequest = chatRequestService.requestChat(requesterId, recipientId);
        return ResponseEntity.ok(chatRequest);
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<ChatRoomDTO> acceptChatRequest(@PathVariable Long requestId) {
        ChatRoomDTO chatRoom = chatRequestService.acceptChatRequest(requestId);
        return ResponseEntity.ok(chatRoom);
    }

    @PostMapping("/decline/{requestId}")
    public ResponseEntity<Void> declineChatRequest(@PathVariable Long requestId) {
        chatRequestService.declineChatRequest(requestId);
        return ResponseEntity.ok().build();
    }
}
