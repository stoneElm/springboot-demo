package com.stone.elm.springboot.demo.basictech.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServerHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 连接建立时触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 建立连接不触发任何事件
        LOGGER.info("有新连接加入！");

    }


}
