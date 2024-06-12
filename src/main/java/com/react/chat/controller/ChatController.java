package com.react.chat.controller;

import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.dto.MemberDTO;
import com.react.chat.service.ChatMessageService;
import com.react.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    // 채팅방 id에 속한 메시지 목록 조회
    @GetMapping("/{roomId}")
    public List<ChatMessageDTO> getChatMessages(@PathVariable Long roomId) {
        log.info("******** ChatController GET /:roomId - roomId : {}", roomId);
        List<ChatMessageDTO> messages = chatMessageService.getMessagesByChatRoomId(roomId);
        return messages;
    }

    // 로그인 사용자 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoomDTO> getChatRooms(@AuthenticationPrincipal MemberDTO member) {
        log.info("******** ChatController GET /rooms - member : {}", member);
        List<ChatRoomDTO> chatRooms = chatRoomService.getAllChatRooms(member);
        return chatRooms;
    }

    // 채팅방 생성
    @PostMapping("/create")
    public Map<String, Long> createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) {
        log.info("******** ChatController POST /create - chatRoomDTO : {}", chatRoomDTO);

        Long room = chatRoomService.createChatRoom(chatRoomDTO);
        return Map.of("room", room);
    }


}
