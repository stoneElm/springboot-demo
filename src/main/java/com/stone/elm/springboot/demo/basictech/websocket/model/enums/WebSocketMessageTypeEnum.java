package com.stone.elm.springboot.demo.basictech.websocket.model.enums;

public enum WebSocketMessageTypeEnum {
    HEARTBEAT("heartbeat", "心跳检测"),
    REFRESH_MESSAGE("Refresh unread", "客户端刷新未读消息");

    WebSocketMessageTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private final String code;
    private final String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
