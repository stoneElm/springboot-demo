package com.stone.elm.springboot.demo.config;

import com.stone.elm.springboot.demo.basictech.websocket.WebSocketServerHandler;
import com.stone.elm.springboot.demo.basictech.websocket.interceptor.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServerHandler(), "/webSocket")
                .setAllowedOrigins("*") // 允许跨域
                .addInterceptors(new AuthHandshakeInterceptor());
    }

    @Bean
    public WebSocketServerHandler webSocketServerHandler() {
        return new WebSocketServerHandler();
    }
}
