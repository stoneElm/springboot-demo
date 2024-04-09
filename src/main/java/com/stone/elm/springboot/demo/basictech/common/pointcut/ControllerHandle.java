package com.stone.elm.springboot.demo.basictech.common.pointcut;

import com.stone.elm.springboot.demo.basictech.common.utils.ControllerHandleUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
@Configuration
public class ControllerHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandle.class);

    // 定义切入点
    @Pointcut("execution(* com.stone.elm.springboot.demo.*.*.controller..*.*(..)) " +
            "|| execution(* com.stone.elm.springboot.demo.*.controller..*.*(..))")
    public void monitor(){}

    @Before("monitor()")  // 顺序二
    public void before(JoinPoint joinPoint) throws Throwable {
        // 调用接口前

    }

    @Around("monitor()")  // 顺序一
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long s = System.currentTimeMillis();

        // 获取调用位置(方法)
        Signature signature = proceedingJoinPoint.getSignature();
        // 获取切点类
        Class declaringType = signature.getDeclaringType();
        // 获取切点方法
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        LOGGER.info("调用: {}$${} 开始", declaringType.getTypeName(), method.getName());

        ControllerHandleUtil.handlePageParam(proceedingJoinPoint);

        Object proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long e = System.currentTimeMillis();
        LOGGER.info("调用: {}$${} 结束" ,  declaringType.getTypeName(), method.getName());
        LOGGER.info("方法总耗时: {}ms", (e - s));
        return proceed;
    }
}
