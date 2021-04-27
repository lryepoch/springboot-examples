package com.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lryepoch
 * @date 2020/12/18 17:25
 * @description TODO 生产者通过send方法向交换机exchange.topic中发送消息，消息中包含不同的"路由键"；
 */
public class TopicSender {

    @Autowired
    private RabbitTemplate template;

    private static final String exchangeName = "exchange.topic";

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicSender.class);


    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    public void send(int index) {
        StringBuilder builder = new StringBuilder("Hello to ");
        int limitIndex = index % keys.length;
        String key = keys[limitIndex];
        builder.append(key).append(' ').append(index + 1);
        String message = builder.toString();

        template.convertAndSend(exchangeName, key, message);
        LOGGER.info("topic生产者 Sent {}", message);
    }
}
