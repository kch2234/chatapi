package com.react.chat.controller;

import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatRoomService;
import com.react.chat.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final ChatRoomService chatRoomService;

    @PostMapping("/find")
    public ResponseEntity<ChatRoomDTO> findMatch(@RequestParam Long memberId) {
        Optional<ChatRoomDTO> chatRoomDTO = matchService.findMatch(memberId);

        if (chatRoomDTO.isPresent()) {
            return ResponseEntity.ok(chatRoomDTO.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestParam Long senderId, @RequestParam Long recipientId) {
        ChatRoomDTO chatRoomDTO = chatRoomService.createChatRoom(senderId, recipientId);
        return ResponseEntity.ok(chatRoomDTO);
    }

    @MessageMapping("/start")
    public void startMatching(Long memberId) {
        matchService.findMatch(memberId).ifPresent(chatRoomDTO -> {
            // 매칭된 채팅방에 대한 추가 처리를 여기서 할 수 있습니다.
        });
    }
}
