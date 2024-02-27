package com.stone.elm.springboot.demo.basictech.common.exception;

public class BusinessException extends BaseException{
    private Object data;
    private static final long serialVersionUID = -7395673441935057898L;

    public BusinessException(String message, String code) {
        super(message, code);
    }

    public BusinessException(String message, String code, Throwable throwable) {
        super(message, throwable, code);
    }

    public BusinessException(String message, String code, String remindType) {
        super(message, code, remindType);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
