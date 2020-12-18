package com.rabbitmq.v1.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 17:29
 * @description TODO @RabbitListener上设置持久化
 *                   @Queue: 当所有消费客户端连接断开后，是否自动删除队列
 *                           true：删除， false：不删除
 *                   @Exchange：当所有绑定队列都不在使用时，是否自动删除交换器
 *                              true：删除， false：不删除
 *
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "topic.message", autoDelete = "false"),
        exchange = @Exchange(value = "topicExchange", type = ExchangeTypes.TOPIC),
        key = "topic.message"))
@Slf4j
public class TopicReceiver1 {

    @RabbitHandler
    public void process(String message) {
        log.info("Topic 消费者 1：" + message);
    }
}
