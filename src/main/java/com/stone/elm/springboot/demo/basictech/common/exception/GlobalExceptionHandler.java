package com.stone.elm.springboot.demo.basictech.common.exception;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseResult<Object> handleException(BusinessException e) {
        return ResultUtils.failResult(e.getMessage(), e.getCode());
    }
}
