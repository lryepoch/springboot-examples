package com.ttl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/4/29 11:03
 * @description TODO
 */
@Component
@Slf4j
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String message){
        rabbitTemplate.convertAndSend(AmqpConfig.LIND_EXCHANGE, AmqpConfig.LIND_QUEUE, message);
        log.info("向普通队列发送：{}", message);
    }
}
