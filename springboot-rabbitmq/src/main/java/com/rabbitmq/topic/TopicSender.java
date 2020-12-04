package com.rabbitmq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 17:25
 * @description TODO 主题模式
 */
@Component
@Slf4j
public class TopicSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1() {
        String context = "hi, I am message 1";
        log.info("生产者：" + context);
        rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
    }

    public void send2() {
        String context = "hi, I am message 2";
        log.info("生产者：" + context);
        rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }
}
