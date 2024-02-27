package com.stone.elm.springboot.demo.basictech.common.response;

public class ResultUtils {
    private ResultUtils() {
    }

    public static <T> ResponseResult<T> wrapResult() {
        return ResultUtils.wrapResult(null);
    }

    public static <T> ResponseResult<T> wrapResult(T t) {
        return new ResponseResult<>(t);
    }

    public static <T> ResponseResult<T> failResult() {
        return new ResponseResult<>(ResponseConstant.FAIL, ResponseConstant.MESSAGE_FAIL);
    }

    public static <T> ResponseResult<T> failResult(String message) {
        return new ResponseResult<>(ResponseConstant.FAIL, message);
    }

    public static <T> ResponseResult<T> failResult(String message, String code) {
        return new ResponseResult<>(code, message);
    }
}
