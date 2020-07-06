package com.example.lixc.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixc
 * @Description 日志参数打印类
 * @createTime 2020/6/12 15:08
 */
@Configuration
@Slf4j
@Aspect
public class AopConfig {

    @Pointcut("execution(* com.example.lixc.controller..*.*(..))")
    public void executeService() {
    }

    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        //获取当前的requestContext
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        //从requestAttributes中获取HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        assert request != null;
        StringBuffer requestURL = request.getRequestURL();
        String method = request.getMethod();
        Enumeration<String> parameterNames = request.getParameterNames();

        Map<String, String> argsMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            argsMap.put(s, request.getParameter(s));
        }
        log.info("请求路径：{},请求方法：{}，请求参数：{}", requestURL, method, argsMap);
    }
}
