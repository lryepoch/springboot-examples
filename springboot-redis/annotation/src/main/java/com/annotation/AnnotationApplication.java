package com.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/9/5 10:02
 * @description TODO Spring Boot 整合 Spring Cache + Redis（实现数据缓存）
 *                   Spring为我们提供了几个注解来支持Spring Cache。其核心主要是@Cacheable和@CacheEvict。
 */
@SpringBootApplication
public class AnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }
}
