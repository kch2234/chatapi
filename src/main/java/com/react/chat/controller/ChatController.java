package com.react.chat.controller;

import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.security.filter.JWTCheckFilter;
import com.react.chat.service.ChatMessageService;
import com.react.chat.service.ChatRoomService;
import com.react.chat.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    @GetMapping("/room/{roomId}")
    public List<ChatMessageDTO> getChatMessages(@PathVariable("roomId") Long roomId) {
        log.info("******** ChatController GET /:roomId - roomId : {}", roomId);
        List<ChatMessageDTO> messages = chatMessageService.getMessagesByChatRoomId(roomId);
        log.info("******** ChatController GET /:roomId - messages : {}", messages);
        return messages;
    }


    // 로그인 사용자 채팅방 목록 조회
    @GetMapping("/list")
    public List<ChatRoomDTO> getMyChatRooms(@RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }

        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        //String email = authentication.getName();
        log.info("Authenticated user's email: {}", member.get("email"));
        List<ChatRoomDTO> chatRooms = chatRoomService.getChatRoomsByMemberEmail(member.get("email").toString());
        log.info("******** ChatController GET /list - chatRooms : {}", chatRooms);
        return chatRooms;
    }

    // 채팅방 생성
    @PostMapping("/room")
    public Map<String, Long> createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) {
        log.info("******** ChatController POST /create - chatRoomDTO : {}", chatRoomDTO);
        Long room = chatRoomService.createChatRoom(chatRoomDTO);
        return Map.of("room", room);
    }


}
