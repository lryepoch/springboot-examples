package com.rabbitmq.v1.direct.object;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 15:54
 * @description TODO
 */
@Component
@RabbitListener(queues = "object")
@Slf4j
public class ObjectReceiver {

    @RabbitHandler
    public void process(User user) {
        log.info("消费者：" + user);
    }
}
