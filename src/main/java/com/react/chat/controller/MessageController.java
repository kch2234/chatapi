package com.react.chat.controller;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.service.ChatMessageService;
import com.react.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat/room/{roomId}/message")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatMessageDTO sendMessage(@DestinationVariable Long roomId, ChatMessageDTO messageDTO) {
        log.info("Received roomId - roomId: {}", roomId);
        log.info("Received message - message: {}", messageDTO);
        ChatRoom chatRoom = chatRoomService.findRoomById(roomId);
        ChatMessage chatMessage = messageDTO.toEntity(chatRoom); // ChatRoom 객체를 엔티티로 변환
        chatMessageService.sendMessage(chatMessage.toDTO());
        return messageDTO;
    }







    @MessageMapping("/chat/info/{token}")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatMessageDTO enter(ChatMessageDTO messageDTO) {
        log.info("Received message - enter: {}", messageDTO);
        chatMessageService.addUser(messageDTO);
        return messageDTO;
    }

    @MessageMapping("/chat/leave")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatMessageDTO leave(ChatMessageDTO messageDTO) {
        log.info("Received message - leave: {}", messageDTO);
        chatMessageService.leaveUser(messageDTO);
        return messageDTO;
    }
}
