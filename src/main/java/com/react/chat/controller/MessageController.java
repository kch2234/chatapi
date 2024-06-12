package com.react.chat.controller;

import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/enter")
    @SendTo("/topic/chat/room")
    public ChatMessageDTO enter(ChatMessageDTO messageDTO) {
        log.info("Received message - enter: {}", messageDTO);
        return chatMessageService.addUser(messageDTO);
    }

    @MessageMapping("/chat/message")
    @SendTo("/topic/chat/room")
    public ChatMessageDTO message(ChatMessageDTO messageDTO) {
        log.info("Received message - message: {}", messageDTO);
        return chatMessageService.sendMessage(messageDTO);
    }

    @MessageMapping("/chat/leave")
    @SendTo("/topic/chat/room")
    public ChatMessageDTO leave(ChatMessageDTO messageDTO) {
        log.info("Received message - leave: {}", messageDTO);
        return chatMessageService.leaveUser(messageDTO);
    }
}
