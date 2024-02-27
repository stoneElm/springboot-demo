package com.stone.elm.springboot.demo.basictech.common.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class BaseException extends HystrixBadRequestException {
    private String code;
    private String remindType;


    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable throwable, String code) {
        super(message, throwable);
        this.code = code;
    }

    public BaseException(String message, String code, String remindType) {
        super(message);
        this.code = code;
        this.remindType = remindType;
    }

    public BaseException(String message, Throwable throwable, String code, String remindType) {
        super(message, throwable);
        this.code = code;
        this.remindType = remindType;
    }

    public String getCode() {
        return code;
    }

    public String getRemindType() {
        return remindType;
    }
}
