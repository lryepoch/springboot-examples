package com.rabbitmq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 17:30
 * @description TODO
 */
@Component
@RabbitListener(queues = "topic.messages")
@Slf4j
public class TopicReceiver2 {

    @RabbitHandler
    public void process(String message) {
        log.info("Topic 消费者 2：" + message);
    }
}
