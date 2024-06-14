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

}
