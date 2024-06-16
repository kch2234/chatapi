package com.react.chat.controller;

import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatMessageService;
import com.react.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return messages;
    }


    // 로그인 사용자 채팅방 목록 조회
    @GetMapping("/list")
    public List<ChatRoomDTO> list() {
        List<ChatRoomDTO> chatRooms = chatRoomService.getAllChatRooms();
        log.info("******** ChatController GET /rooms - chatRooms : {}", chatRooms);
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
