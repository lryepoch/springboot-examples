package com.thymeleaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/5/19 18:54
 * @description TODO springboot + shiro + thymeleaf
 */
@MapperScan(basePackages = "com.thymeleaf.mapper")
@SpringBootApplication
public class ShiroThymeleafApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroThymeleafApplication.class,args);
    }
}
