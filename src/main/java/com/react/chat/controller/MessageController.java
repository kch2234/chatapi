package com.react.chat.controller;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final ChatMessageService chatMessageService;
    @MessageMapping("/chat/enter")
    @SendTo("/sub/chat")
    public ChatMessageDTO enter(ChatMessageDTO messageDTO) {
        log.info("Received message - enter: {}", messageDTO);
        chatMessageService.addUser(messageDTO);
        return messageDTO;
    }

    @MessageMapping("/chat/message")
    @SendTo("/sub/chat/message")
    public ChatMessageDTO message(ChatMessageDTO messageDTO) {
        log.info("Received message - message: {}", messageDTO);
        chatMessageService.sendMessage(messageDTO);
        return messageDTO;
    }

    @MessageMapping("/chat/leave")
    @SendTo("/sub/chat")
    public ChatMessageDTO leave(ChatMessageDTO messageDTO) {
        log.info("Received message - leave: {}", messageDTO);
        chatMessageService.leaveUser(messageDTO);
        return messageDTO;
    }
}
