package com.stone.elm.springboot.demo.business.user.model.enums;

public enum LoginModelEnum {
    LOGIN_MODEL_PASSWORD("01", "账户密码登录"),
    LOGIN_MODEL_PHONE("02", "手机验证登录");

    LoginModelEnum(String loginModelCode, String loginModelDesc) {
        this.loginModelCode = loginModelCode;
        this.loginModelDesc = loginModelDesc;
    }

    private final String loginModelCode;
    private final String loginModelDesc;

    public String getLoginModelCode() {
        return loginModelCode;
    }

    public String getLoginModelDesc() {
        return loginModelDesc;
    }
}
