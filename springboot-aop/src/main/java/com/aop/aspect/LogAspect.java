package com.aop.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author lryepoch
 * @date 2021/1/8 14:05
 * @description 日志切面
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.aop.controller.*.*(..))")
    public void webLog() {
    }

    //前置通知
    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("@Before，前置通知......");
        //接收请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录下请求内容
        System.out.println("URL: " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD: " + request.getMethod());
        System.out.println("IP: " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS: " + Arrays.toString(joinPoint.getArgs()));

    }

    //后置正常返回时通知
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("@AfterReturning，方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp) {
        System.out.println("@AfterThrowing，方法异常时执行.....");
    }

    //后置最终通知，final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp) {
        System.out.println("@After，方法最后执行.....");
    }

    //环绕通知，环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("@Around，方法环绕start.....");
        try {
            Object obj = joinPoint.proceed();
            System.out.println("@Around，方法环绕proceed后，结果是 :" + obj);
            return obj;
        } catch (Throwable e) {
            throw e;
        }
    }

}

