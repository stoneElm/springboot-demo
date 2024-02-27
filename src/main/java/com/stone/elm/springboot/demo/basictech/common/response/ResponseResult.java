package com.stone.elm.springboot.demo.basictech.common.response;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private transient T data;
    private long total;

    public ResponseResult() {
    }

    public ResponseResult(T data) {
        this(ResponseConstant.SUCCESS, ResponseConstant.MESSAGE_SUCCESS, data);
    }

    public ResponseResult(String code, String message) {
        this(code, message, null);
    }

    public ResponseResult(String code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
