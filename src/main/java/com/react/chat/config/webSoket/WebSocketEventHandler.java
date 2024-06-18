package com.react.chat.config.webSoket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Slf4j
@Component
public class WebSocketEventHandler {

    @EventListener
    public void handleWebSocketSessionConnect(SessionConnectEvent event) {
        log.info("WebSocket Session Connect: {}", event);
        logConnectEvent(event);
    }

    private void logConnectEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        if (accessor != null && accessor.getUser() != null) {
            String username = accessor.getUser().getName();
            log.info("WebSocket {}: username={}", event.getClass().getSimpleName(), username);
        } else {
            log.info("WebSocket {}: No User Information", event.getClass().getSimpleName());
        }
    }

    @EventListener
    public void handleWebSocketSessionConnected(SessionConnectedEvent event) {
        log.info("WebSocket Session Connected: {}", event);
    }

    @EventListener
    public void handleWebSocketSessionDisconnect(SessionDisconnectEvent event) {
        log.info("WebSocket Session Disconnected: {}", event);
    }

    @EventListener
    public void handleWebSocketSessionSubscribe(SessionSubscribeEvent event) {
        log.info("WebSocket Session Subscribe: {}", event);
    }

    @EventListener
    public void handleWebSocketSessionUnsubscribe(SessionUnsubscribeEvent event) {
        log.info("WebSocket Session Unsubscribe: {}", event);
    }
}
