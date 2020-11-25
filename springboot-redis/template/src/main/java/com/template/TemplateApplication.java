package com.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author lryepoch
 * @date 2020/9/5 11:23
 * @description TODO 与使用注解方式不同，注解方式可以零配置，只需引入依赖并在启动类上加上 @EnableCaching 注解就可以使用；
 *                   而使用 RedisTemplate 方式麻烦些，需要做一些配置。
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}
