package com.react.chat.config.webSoket;

import com.react.chat.config.webSoket.interceptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSoketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정: /topic 및 /queue로 시작하는 경로를 메시지 브로커가 처리하도록 설정
        config.enableSimpleBroker("/topic", "/queue");
        // 애플리케이션 목적지 접두사 설정: /app으로 시작하는 경로는 애플리케이션 핸들러로 라우팅
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트 등록: 클라이언트가 이 엔드포인트를 통해 WebSocket 연결을 시도
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*") // CORS 설정
                .addInterceptors(webSocketHandshakeInterceptor) // 핸드셰이크 인터셉터 추가
                .withSockJS(); // SockJS 지원 활성화
    }
}
