package com.v1.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/25 16:03
 * @description TODO 广播模式
 */
@Component
@Slf4j
public class FanoutSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){
        String context = "hi, fanout msg";
        log.info("生产者:" + context);
        rabbitTemplate.convertAndSend("fanoutExchange","",context);
    }
}
