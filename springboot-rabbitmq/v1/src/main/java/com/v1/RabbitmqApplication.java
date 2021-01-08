package com.v1;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/5/25 15:38
 * @description TODO springboot + rabbitmq
 *                   v1介绍了3种模式：指定队列模式、广播模式、主题模式
 */
@EnableRabbit
@SpringBootApplication
public class RabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
