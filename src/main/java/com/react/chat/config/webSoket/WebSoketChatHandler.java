package com.react.chat.config.webSoket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.chat.dto.ChatMessageDTO;
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
    private final ObjectMapper mapper;

    // 현재 연결된 웹소켓 세션들을 담는 Set
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // 채팅방 당 연결된 세션을 담은 Map, 'Map<채팅방 id, Set<세션>>'의 형태로 세션에 저장
    // 채팅 메시지를 보낼 채팅방을 찾고, 해당 채팅방에 속한 세션들에게 메시지를 전송
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 메모리 상에 현재 연결된 웹소켓을 담고, 세션이 추가되거나 종료되면 반영
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    //소켓 통신 시 메세지의 전송을 다루는 부분
    // TextWebSoketHandler 클래스의 handleTextMessage() 메서드를 오버라이딩하여 구현
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        // 웹소켓 통신 메시지를 TextMessage로 받고, 이를 'mapper'로 파싱하여 ChatMessageDTO로 변환

        // 페이로드 -> chatMessageDto로 변환
        ChatMessageDTO chatMessageDTO = mapper.readValue(payload, ChatMessageDTO.class);
        log.info("session {}", chatMessageDTO.toString());

        Long chatRoomId = chatMessageDTO.getChatRoomId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        // message 에 담긴 타입을 확인한다.
        // 이때 message 에서 getType 으로 가져온 내용이
        // ChatDTO 의 열거형인 MessageType 안에 있는 ENTER 과 동일한 값이라면
        if (chatMessageDTO.getMessageType().equals(chatMessageDTO.getMessageType().ENTER)) {
            // sessions 에 넘어온 session 을 담고,
            chatRoomSession.add(session);
        }
        if (chatRoomSession.size()>=3) {
            removeClosedSession(chatRoomSession);
        }
        sendMessageToChatRoom(chatMessageDTO, chatRoomSession);

    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(ChatMessageDTO chatMessageDTO, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDTO));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
