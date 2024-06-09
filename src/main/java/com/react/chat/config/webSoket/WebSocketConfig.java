package com.react.chat.config.webSoket;

import com.react.chat.config.webSoket.interceptor.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketHandshakeInterceptor handshakeInterceptor;

    public WebSocketConfig(WebSocketHandshakeInterceptor handshakeInterceptor) {
        this.handshakeInterceptor = handshakeInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Client에서 websocket 연결할 때 사용할 API 경로를 설정 - 채팅용
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .addInterceptors(handshakeInterceptor)
                .withSockJS();
        // Client에서 websocket 연결할 때 사용할 API 경로를 설정 - 매칭용
        registry.addEndpoint("/match")
                .setAllowedOriginPatterns("*")
                .addInterceptors(handshakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 구독 경로
        config.enableSimpleBroker("/sub");
        // 메시지 발행 경로 설정
        config.setApplicationDestinationPrefixes("/pub");
    }

}
