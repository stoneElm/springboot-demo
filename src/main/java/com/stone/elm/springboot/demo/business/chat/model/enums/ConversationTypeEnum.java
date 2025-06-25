package com.stone.elm.springboot.demo.business.chat.model.enums;

public enum ConversationTypeEnum {

    FRIEND("01", "好友"),
    GROUP("02", "群组");

    ConversationTypeEnum(String code, String value) {
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
