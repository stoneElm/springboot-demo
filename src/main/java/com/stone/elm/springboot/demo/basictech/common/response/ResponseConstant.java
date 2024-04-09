package com.stone.elm.springboot.demo.basictech.common.response;

public class ResponseConstant {
    private ResponseConstant() {
    }

    public static final String SUCCESS = "00000";

    // 业务异常
    public static final String FAIL = "-1";

    // 参数检验异常
    public static final String VALIDATION_FAILED = "-2";

    public static final String MESSAGE_SUCCESS = "success";
    public static final String MESSAGE_FAIL = "fail";
}
