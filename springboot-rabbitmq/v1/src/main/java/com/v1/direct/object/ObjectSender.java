package com.v1.direct.object;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 15:54
 * @description TODO 指定队列模式
 */
@Component
@Slf4j
public class ObjectSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(User user){
        log.info("生产者："+user.toString());
        rabbitTemplate.convertAndSend("object", user);
    }
}
