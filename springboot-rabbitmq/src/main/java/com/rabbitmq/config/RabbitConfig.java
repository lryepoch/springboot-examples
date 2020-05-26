package com.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lryepoch
 * @date 2020/5/25 17:18
 * @description TODO 注册
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }

    @Bean
    public Queue lryQueue(){
        return new Queue("lry");
    }

    @Bean
    public Queue objectQueue(){
        return new Queue("object");
    }

}