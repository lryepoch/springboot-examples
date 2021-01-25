package com.lry.kafka.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lryepoch
 * @date 2020/11/24 19:57
 * @description TODO 生产者
 */
@Component
@Slf4j
public class Kafka2Producer {

    public String topic = KafkaConfig.topic;

    //注入自定义生产者
    @Resource
    KafkaProducer producer;

    //可定义一个controller调用该方法
    public void producer(int i) {
        String key = i+"";
        String message = "User:{'id':1,'username':'张三','age':18}";
        log.info("==>发送消息:{}, {}", key, message);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        try {
            //异步发送消息
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    //kafka元数据
                    if (exception == null) {
                        System.out.println("message send to partition，" + metadata.partition() + ":" + metadata.offset());
                    }
                }
            });
            producer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}