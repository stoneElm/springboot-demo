package com.stone.elm.springboot.demo.basictech.websocket.utils;

import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.basictech.websocket.WebSocketServerHandler;
import com.stone.elm.springboot.demo.basictech.websocket.model.WebSocketMessageModel;
import com.stone.elm.springboot.demo.basictech.websocket.model.enums.WebSocketMessageTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WebSocketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketUtil.class);

    private static WebSocketServerHandler webSocketServerHandler = null;

    public WebSocketUtil(WebSocketServerHandler webSocketServerHandler) {
        this.webSocketServerHandler = webSocketServerHandler;
    }

    public static void notifyClientRefreshMessage(WebSocketSession session){
        if (!session.isOpen()) {
            LOGGER.info("当前会话已关闭，无法发送消息！");
        }

        WebSocketMessageModel socketMessageModel = new WebSocketMessageModel();
        socketMessageModel.setMessageType(WebSocketMessageTypeEnum.REFRESH_All_MESSAGE.getCode());

        String message = JsonUtil.convertObjectToJson(socketMessageModel);

        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            LOGGER.info("消息发送失败！");
            e.printStackTrace();
        }
    }

    public static void sendMessageForUserIDList(List<Long> userIDList, WebSocketMessageModel messageModel) {
        if (CollectionUtils.isEmpty(userIDList)) {
            return;
        }

        for (Long userID : userIDList) {
            sendMessageForUserID(userID, messageModel);
        }
    }

    public static void sendMessageForUserID(Long userID, WebSocketMessageModel messageModel) {
        Map<Long, WebSocketSession> sessions = webSocketServerHandler.getSessions();

        WebSocketSession session = sessions.get(userID);

        if (Objects.isNull(session)) {
            return;
        }

        if (!session.isOpen()) {
            LOGGER.info("当前会话已关闭，无法发送消息！");
            return;
        }

        String message = JsonUtil.convertObjectToJson(messageModel);

        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            LOGGER.info("消息发送失败！");
            e.printStackTrace();
        }
    }
}
