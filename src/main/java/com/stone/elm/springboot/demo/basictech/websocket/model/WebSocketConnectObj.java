package com.stone.elm.springboot.demo.basictech.websocket.model;

import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;

/**
 * 用于存放websocket对象信息
 */
public class WebSocketConnectObj {

    private Long userID;

    private String uuid;

    private Session session;

    private WebSocketSession webSocketSession;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
