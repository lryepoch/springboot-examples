package com.lry.kafka.annotation.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    public void send(int key) {
        Message message = new Message();
        message.setId("kafka" + System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());

        log.info("==>发送消息:{}, {}", key, message);
        kafkaTemplate.send("test", key + "", gson.toJson(message));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
