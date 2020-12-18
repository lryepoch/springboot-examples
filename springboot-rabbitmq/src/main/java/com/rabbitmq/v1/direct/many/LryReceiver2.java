package com.rabbitmq.v1.direct.many;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 16:42
 * @description TODO
 */
@Component
@Slf4j
@RabbitListener(queues = "lry")
public class LryReceiver2 {

    @RabbitHandler
    public void process(String lry){
        log.info("消费者 2："+lry);
    }
}
