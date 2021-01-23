package com.lry.kafka.client;

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
public class Kafka_Producer {

    public String topic = KafkaConfig.topic;

    //注入自定义生产者
    @Resource
    KafkaProducer producer;

    //可定义一个controller调用该方法
    public void producer() {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, Kafka!");
        try {
            //异步
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("message send to partition，" + metadata.partition() + ":" + metadata.offset());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}