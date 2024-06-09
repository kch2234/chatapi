package com.react.chat.config.webSoket.interceptor;

import com.react.chat.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpHeaders headers = request.getHeaders();
        List<String> authHeaders = headers.get("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String authHeader = authHeaders.get(0);
            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    // JWT 토큰 검증 로직 추가
                    Map<String, Object> claims = JWTUtil.validateToken(token);
                    attributes.put("claims", claims);
                    return true;
                } catch (Exception e) {
                    log.error("JWT 검증 실패", e);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return false;
                }
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 후처리 로직 필요시 추가
    }
}
