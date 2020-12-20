package com.v1;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/5/25 15:38
 * @description TODO springboot + rabbitmq
 */
@EnableRabbit
@SpringBootApplication
public class RabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
