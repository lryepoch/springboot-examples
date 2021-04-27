package com.x.controller;

import com.x.config.RabbitConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/4/27 16:31
 * @description TODO rabbitmq生产者
 */
@Component
@Slf4j
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
    * 发送消息
    * @param object      发送对象
    * @param millisecond 延时（毫秒）
    */
    public void sendDelayMessage(Object object, long millisecond){
        this.rabbitTemplate.convertAndSend(
                RabbitConst.DELAY_EXCHANGE,
                RabbitConst.DELAY_KEY,
                object.toString(),
                message -> {
                    message.getMessageProperties().setHeader("x-delay", millisecond);
                    return message;
                }
        );
    }
}
