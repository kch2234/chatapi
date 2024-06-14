package com.react.chat.config.webSoket.interceptor;

import com.react.chat.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("WebSocket Command: {}", accessor.getCommand());
        log.info("Headers: {}", accessor.toNativeHeaderMap());
        log.info("Payload: {}", new String((byte[]) message.getPayload()));

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");
            log.info("WebSocket CONNECT - authToken: {}", authToken);

            if (authToken != null && authToken.startsWith("Bearer ")) {
                String token = authToken.substring(7);
                log.info("WebSocket CONNECT - token: {}", token);

                try {
                    if (JWTUtil.isValidToken(token)) {
                        String username = JWTUtil.getUsernameFromToken(token);
                        log.info("WebSocket CONNECT - username: {}", username);
                        accessor.setUser(new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()));
                    } else {
                        log.error("Invalid JWT token");
                        throw new IllegalArgumentException("Invalid JWT token");
                    }
                } catch (Exception e) {
                    log.error("JWT 검증 실패: {}", e.getMessage());
                    throw new IllegalArgumentException("Invalid JWT token");
                }
            } else {
                log.error("Authorization header not found or invalid");
                throw new IllegalArgumentException("Authorization header not found or invalid");
            }
        }
        return message;
    }
}
