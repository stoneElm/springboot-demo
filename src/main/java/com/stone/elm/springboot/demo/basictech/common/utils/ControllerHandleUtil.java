package com.stone.elm.springboot.demo.basictech.common.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;

public class ControllerHandleUtil {
    private ControllerHandleUtil () {}

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandleUtil.class);

    public static void handlePageParam(ProceedingJoinPoint proceedingJoinPoint) {

        Object[] args = proceedingJoinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if (Objects.nonNull(obj)) {
                try {
                    setIndexStartByObj(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("处理分页信息异常，请稍后再试!");
                }
            }
        }

    }

    private static void setIndexStartByObj(Object obj) throws IllegalAccessException {
        Class<?> resultClz = obj.getClass();

        Integer pageNo = null;
        Integer pageSize = null;

        Field[] fieldInfo = resultClz.getSuperclass().getDeclaredFields();
        for (Field field : fieldInfo) {
            if ("pageNo".equals(field.getName())) {
                field.setAccessible(true);
                Object var = field.get(obj);
                if (Objects.isNull(var)) {
                    break;
                }
                pageNo = Integer.valueOf(var.toString());
            }
            if ("pageSize".equals(field.getName())) {
                field.setAccessible(true);
                Object var = field.get(obj);
                if (Objects.isNull(var)) {
                    break;
                }
                pageSize = Integer.valueOf(var.toString());
            }
        }

        if (Objects.nonNull(pageNo) && Objects.nonNull(pageSize)) {
            Integer indexStart = (pageNo - 1) * pageSize;
            LOGGER.info("当前请求接受到分页信息--- pageNo: {}, pageSize: {}, 处理得到indexStart: {}", pageNo, pageSize, indexStart);
            for (Field field : fieldInfo) {
                if ("indexStart".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(obj, indexStart);
                }
            }
        }
    }
}
