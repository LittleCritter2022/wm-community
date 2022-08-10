package com.wm.wmcommunity.common.aspect;

import com.wm.wmcommunity.common.annotations.LoginAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing YUXiangRuanJian Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> LoginAspect
 * Product:WMing
 * Creator: Jerry(0120)
 * Date Created: 2022/8/8
 * Description:aop实现登录日志记录
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/8/8                 Jerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Jerry(0120)
 */
@Aspect
@Component
public class LoginAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAspect.class);

    /**
     * 切点实例1
     * 通过自定义注解（@annotation）来记录打了自定义注解的接口的日志信息
     */
    @Pointcut("@annotation(com.wm.wmcommunity.common.annotations.LoginAnnotation)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行开始时间
        long startTime = System.currentTimeMillis();
        //执行方法
        Object proceed = joinPoint.proceed();
        //执行方法所需的时间
        long time = System.currentTimeMillis() - startTime;
        //记录日志
        recordLog(joinPoint, time);
        return proceed;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        //通过joinPoint获取签名方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LoginAnnotation annotation = method.getAnnotation(LoginAnnotation.class);
        LOGGER.info("============ Log Start ==============");
        LOGGER.info("module:{}", annotation.module());
        LOGGER.info("operator:{}", annotation.operator());

        //获取执行的类和响应的方法
        String className = joinPoint.getTarget().getClass().getName();
        Method methodName = signature.getMethod();
        LOGGER.info("Request method:{}", className + "." + methodName);

        //请求地址
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String remoteHost = requestAttributes.getRequest().getRemoteHost();
        LOGGER.info("Request IP:{}", remoteHost);
        LOGGER.info("Execute need Time:{}ms", time);
        LOGGER.info("=========== Log End ================");
    }

    /**
     * 切点实例2
     * 通过execution来定义切点信息
     * 使用AOP记录日志
     */
    /*@Pointcut("execution(* com.wm.wmcommunity.service.*.*(..))")
    public void pointCut1() {
    }

    @Before("pointCut1()")
    public void before(ProceedingJoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getRemoteHost();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        LOGGER.info("用户{}，在{}这个时间访问了{}", ip, nowTime, target);
    }*/

}
