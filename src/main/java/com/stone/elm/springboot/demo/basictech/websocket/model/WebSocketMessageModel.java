package com.stone.elm.springboot.demo.basictech.websocket.model;

public class WebSocketMessageModel {
    private String messageType;
    private String messageContent;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
