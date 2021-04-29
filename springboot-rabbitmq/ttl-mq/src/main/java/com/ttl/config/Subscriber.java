package com.ttl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/4/29 11:07
 * @description TODO
 */
@Component
@Slf4j
public class Subscriber {

    @RabbitListener(queues = AmqpConfig.LIND_DEAD_QUEUE)
    public void customerSign(String data){
        log.info("从死信队列拿到数据：{}", data);
    }
}
