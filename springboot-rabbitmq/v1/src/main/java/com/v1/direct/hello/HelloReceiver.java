package com.v1.direct.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 15:44
 * @description TODO
 */
@Component
@RabbitListener(queues = "hello")
@Slf4j
public class HelloReceiver {

    @RabbitHandler
    public void process(String hello) {
        log.info("消费者:" + hello);
    }
}
