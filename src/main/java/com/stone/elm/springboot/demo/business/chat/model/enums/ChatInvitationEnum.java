package com.stone.elm.springboot.demo.business.chat.model.enums;

public enum ChatInvitationEnum {

    PENDING("01", "待同意"),
    AGREED("02", "已同意"),
    REJECTED("03", "已拒绝");

    ChatInvitationEnum(String code, String value) {
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
