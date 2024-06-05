package com.react.chat.controller;

import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final ChatMessageService chatMessageService;

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