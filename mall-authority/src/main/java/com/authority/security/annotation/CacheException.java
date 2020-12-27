package com.authority.security.annotation;

import java.lang.annotation.*;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 18:10
 * @Description 自定义注解，有该注解的缓存方法会抛出异常
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
