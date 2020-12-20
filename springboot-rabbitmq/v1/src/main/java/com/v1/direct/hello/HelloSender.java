package com.v1.direct.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/5/25 15:41
 * @description TODO 指定队列模式
 */
@Component
@Slf4j
public class HelloSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello，" + new Date();
        log.info("生产者:" + context);
        rabbitTemplate.convertAndSend("hello", context);
    }
}
