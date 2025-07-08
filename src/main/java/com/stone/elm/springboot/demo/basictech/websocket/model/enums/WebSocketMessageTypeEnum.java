package com.stone.elm.springboot.demo.basictech.websocket.model.enums;

public enum WebSocketMessageTypeEnum {
    HEARTBEAT("Heartbeat", "心跳检测"),
    REFRESH_All_MESSAGE("Refresh all message", "客户端刷新会话邀请信息和未读消息信息"),
    REFRESH_SESSION_INVITATION("Refresh session invitation", "客户端刷新会话邀请信息"),
    REFRESH_UNREAD_MESSAGE("Refresh unread message", "客户端刷新未读消息");

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
