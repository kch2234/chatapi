package com.react.chat.config.webSoket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.chat.domain.enumFiles.MessageType;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.service.ChatMessageService;
import com.react.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSoketChatHandler extends TextWebSocketHandler {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ObjectMapper mapper;
    // 현재 연결된 웹소켓 세션들을 담는 Set
    private final Set<WebSocketSession> sessions = new HashSet<>();
    // 채팅방 당 연결된 세션을 담은 Map, 'Map<채팅방 id, Set<세션>>'의 형태로 세션에 저장
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session);

        Long chatRoomId = getChatRoomId(session);
        if (chatRoomId != null) {
            chatRoomService.addSessionToChatRoom(chatRoomId, session);
            chatRoomSessionMap.computeIfAbsent(chatRoomId, s -> new HashSet<>()).add(session);

            // 사용자 정보 확인
            Map<String, Object> claims = (Map<String, Object>) session.getAttributes().get("claims");
            if (claims != null) {
                log.info("User {} connected", claims.get("email"));
            } else {
                log.error("사용자 정보를 찾을 수 없습니다.");
                session.close(CloseStatus.NOT_ACCEPTABLE);
            }
        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    // 소켓 통신 시 메시지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // 웹소켓 통신 메시지를 TextMessage로 받고, 이를 'mapper'로 파싱하여 ChatMessageDTO로 변환
        ChatMessageDTO chatMessageDTO = mapper.readValue(payload, ChatMessageDTO.class);
        log.info("session {}", chatMessageDTO.toString());

        Long chatRoomId = chatMessageDTO.getRoomId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        chatRoomSessionMap.computeIfAbsent(chatRoomId, s -> new HashSet<>()).add(session);

        // 메시지 타입에 따라 처리
        if (chatMessageDTO.getMessageType().equals(MessageType.ENTER)) {
            chatRoomSessionMap.get(chatRoomId).add(session);
            chatMessageService.addUser(chatMessageDTO);
        }else if (chatMessageDTO.getMessageType().equals(MessageType.LEAVE)) {
            chatRoomSessionMap.get(chatRoomId).remove(session);
            chatMessageService.leaveUser(chatMessageDTO);
        } else if (chatMessageDTO.getMessageType().equals(MessageType.MESSAGE)) {
            chatMessageService.sendMessage(chatMessageDTO);
        }

        // 연결이 끊어진 세션 제거
        removeClosedSessions(chatRoomSessionMap.get(chatRoomId));
        // 채팅방에 메시지 전송
        sendMessageToChatRoom(chatMessageDTO, chatRoomSessionMap.get(chatRoomId));
        // 메시지 저장
        chatMessageService.saveMessage(chatMessageDTO);
    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
        Long chatRoomId = getChatRoomId(session);
        chatRoomService.removeSessionFromChatRoom(chatRoomId, session);
        chatRoomSessionMap.getOrDefault(chatRoomId, new HashSet<>()).remove(session);
    }
    private Long getChatRoomId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String[] segments = uri.split("/");
        return segments.length > 2 ? Long.valueOf(segments[2]) : null;
    }


    // ====== 채팅 관련 메소드 ======

    private void removeClosedSessions(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sess.isOpen());
    }

    private void sendMessageToChatRoom(ChatMessageDTO chatMessageDTO, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDTO));
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}