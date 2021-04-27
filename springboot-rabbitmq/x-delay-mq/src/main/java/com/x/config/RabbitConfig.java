package com.x.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2021/4/27 16:23
 * @description TODO rabbitmq配置类
 */
@Configuration
public class RabbitConfig {

    /**
    * 延时队列交换机
    */
    @Bean
    public CustomExchange delayExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitConst.DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    /**
    * 延时队列
    */
    @Bean
    public Queue delayQueue(){
        return new Queue(RabbitConst.DELAY_QUEUE, true);
    }

    /**
    * 给延时队列绑定交换机
    */
    @Bean
    public Binding delayBinding(Queue delayQueue, CustomExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(RabbitConst.DELAY_KEY).noargs();
    }
}
