package com.jsp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/5/19 18:54
 * @description TODO springboot + shiro + jsp
 */
@MapperScan(basePackages = "com.jsp.mapper")
@SpringBootApplication
public class ShiroJspApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroJspApplication.class, args);
    }
}
