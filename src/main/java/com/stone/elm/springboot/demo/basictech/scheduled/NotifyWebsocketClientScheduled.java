package com.stone.elm.springboot.demo.basictech.scheduled;

import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.basictech.websocket.WebSocketServerHandler;
import com.stone.elm.springboot.demo.basictech.websocket.model.WebSocketMessageModel;
import com.stone.elm.springboot.demo.basictech.websocket.model.enums.WebSocketMessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Component
public class NotifyWebsocketClientScheduled {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyWebsocketClientScheduled.class);

    private final WebSocketServerHandler webSocketServerHandler;

    public NotifyWebsocketClientScheduled(WebSocketServerHandler webSocketServerHandler) {
        this.webSocketServerHandler = webSocketServerHandler;
    }

    // 每5秒执行一次（心跳检测）
    @Scheduled(fixedRate = 20000)
    public void heartbeat() {
        Map<String, WebSocketSession> sessions = webSocketServerHandler.getSessions();

        sessions.forEach((userID, webSocketSession) -> {
            try {
                if (webSocketSession.isOpen()) {
                    WebSocketMessageModel webSocketMessageModel = new WebSocketMessageModel();
                    webSocketMessageModel.setMessageType(WebSocketMessageTypeEnum.HEARTBEAT.getCode());
                    webSocketSession.sendMessage(new TextMessage(JsonUtil.convertObjectToJson(webSocketMessageModel)));
                } else {
                    sessions.remove(userID);
                }
            } catch (IOException e) {
                // 消息发送失败
                e.printStackTrace();
            }
        });

        LOGGER.info("webSocket heartbeat 定时任务: 本次执行 {} 条数据", sessions.size());
    }
}
