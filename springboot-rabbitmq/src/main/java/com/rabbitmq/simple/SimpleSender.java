package com.rabbitmq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lryepoch
 * @date 2020/12/18 16:26
 * @description TODO 生产者通过send方法向队列simple.hello中发送消息
 */
public class SimpleSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String queueName = "simple.hello";

    public void send(int no) {
        String message = "Hello World!";
        this.template.convertAndSend(queueName, message);
        LOGGER.info(" [" + no + "] Sent '{}'", message);
    }
}
