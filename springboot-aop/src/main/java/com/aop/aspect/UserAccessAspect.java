package com.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/1/8 13:48
 * @description TODO
 */
@Component
@Aspect
public class UserAccessAspect {

    @Pointcut(value = "@annotation(com.aop.aspect.UserAccess)")
    public void access() {
    }

    @Before("access()")
    public void deBefore(JoinPoint joinPoint) {
        System.out.println("@Before，second before");
    }

    @Around("@annotation(userAccess)")
    public Object around(ProceedingJoinPoint point, UserAccess userAccess) {
        //获取注解里的值
        System.out.println("@Around，second around前: " + userAccess.desc());
        try {
            Object proceed = point.proceed();
            System.out.println("@Around，second around后: " + userAccess.desc());
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
