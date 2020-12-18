package com.rabbitmq.v1.direct.many;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 16:39
 * @description TODO 特殊指定队列模式
 */
@Component
@Slf4j
public class LrySender1 {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "spring boot lry queue" + i;
        log.info("生产者1：" + context);
        rabbitTemplate.convertAndSend("lry", context);
    }
}
