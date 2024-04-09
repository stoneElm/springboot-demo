package com.stone.elm.springboot.demo.basictech.common.exception;

public class SystemException extends BaseException{
    private static final long serialVersionUID = -53711580203962790L;

    public SystemException(String message, Throwable throwable, String code) {
        super(message, throwable, code);
    }

    public SystemException(String message, String code) {
        super(message, code);
    }
}
