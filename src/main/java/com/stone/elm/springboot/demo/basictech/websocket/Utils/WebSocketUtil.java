package com.stone.elm.springboot.demo.basictech.websocket.Utils;

import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.basictech.websocket.model.WebSocketMessageModel;
import com.stone.elm.springboot.demo.basictech.websocket.model.enums.WebSocketMessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketUtil {
    private WebSocketUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketUtil.class);

    public static void notifyClientRefreshMessage(WebSocketSession session){
        if (!session.isOpen()) {
            LOGGER.info("当前会话已关闭，无法发送消息！");
        }

        WebSocketMessageModel socketMessageModel = new WebSocketMessageModel();
        socketMessageModel.setMessageType(WebSocketMessageTypeEnum.REFRESH_MESSAGE.getCode());

        String message = JsonUtil.convertObjectToJson(socketMessageModel);

        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            LOGGER.info("消息发送失败！");
            e.printStackTrace();
        }
    }
}
