package com.react.chat.config.webSoket.interceptor;

import com.react.chat.security.filter.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("WebSocket Handshake Interceptor - beforeHandshake");

        String authValue = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("Authorization Header: {}", authValue);

        if (authValue != null && authValue.startsWith("Bearer ")) {
            String token = authValue.substring(7);
            try {
                Claims claims = jwtTokenProvider.getClaimsFromToken(token);
                if (claims != null) {
                    attributes.put("claims", claims);
                    return true;
                }
            } catch (Exception e) {
                log.warn("Invalid JWT token: {}", e.getMessage());
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
