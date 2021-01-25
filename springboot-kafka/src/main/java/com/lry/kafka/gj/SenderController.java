package com.lry.kafka.gj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/1/25 10:20
 * @description TODO 生产消息。单例模式实现kakfa。
 */
@RestController
@Slf4j
public class SenderController {
    public static final String TOPIC = "testly1";

    @GetMapping(value = "/sender")
    public String sender() {
        for (int i = 0; i < 50; i++) {
            String key = i+"";
            String message = "User:{'id':1,'username':'张三','age':18}";
            log.info("==>发送消息:{}, {}", key, message);
            KafkaInnerProducer.getInstance().sendMessage(TOPIC, key, message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "send successfully";
    }
}