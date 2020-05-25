package com.rabbitmq.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 16:11
 * @description TODO
 */
@Component
@RabbitListener(queues = "fanout.B")
@Slf4j
public class FanoutReceiverB {

    @RabbitHandler
    public void process(String message) {
        log.info("fanout 消费者 B:" + message);
    }
}
