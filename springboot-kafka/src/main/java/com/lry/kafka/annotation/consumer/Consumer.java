package com.lry.kafka.annotation.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("【**message**】:" + message);
        }

        log.info("【**接收消息**】: offset:" + record.offset() + ",key:" + record.key() + ",value:" + record.value());

    }
}
