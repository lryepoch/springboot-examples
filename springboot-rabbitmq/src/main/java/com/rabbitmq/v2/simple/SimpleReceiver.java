package com.rabbitmq.v2.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author lryepoch
 * @date 2020/12/18 16:30
 * @description TODO 消费者从队列simple.hello中获取消息
 */
@RabbitListener(queues = "simple.hello")
public class SimpleReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReceiver.class);

    @RabbitHandler
    public void receive(String in){
        LOGGER.info(" [x] Received '{}'", in);
    }
}
