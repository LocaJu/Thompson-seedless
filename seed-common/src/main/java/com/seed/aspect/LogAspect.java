package com.seed.aspect;

import com.alibaba.fastjson.JSON;
import com.seed.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 22:21
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.seed.annotation.SystemLog)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
            handleAfter(result);
        }finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return result;
    }


    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog=getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method : {}.{}", joinPoint.getSignature().getDeclaringType(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args : {}", JSON.toJSONString(joinPoint.getArgs()));
    }




    private void handleAfter(Object result) {
        // 打印出参
        log.info("Response : {}", JSON.toJSONString(result));
    }
}
