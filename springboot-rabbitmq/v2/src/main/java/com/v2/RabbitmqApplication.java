package com.v2;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/5/25 15:38
 * @description TODO springboot + rabbitmq
 *                   v2介绍了5种：简单模式、工作模式、发布/订阅模式（广播模式）、路由模式、通配符模式（主题模式）
 */
@EnableRabbit
@SpringBootApplication
public class RabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
