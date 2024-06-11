package com.react.chat.config.webSoket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.util.Objects;

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

        Authentication authentication = (Authentication) Objects.requireNonNull(accessor.getUser());
        String username = authentication.getName();
        String userRole = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new IllegalArgumentException("User role is not exist."));

        log.info("WebSocket {}: username={}, userRole={}", event.getClass().getSimpleName(), username, userRole);
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
