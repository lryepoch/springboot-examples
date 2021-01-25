package com.lry.kafka.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author lryepoch
 * @date 2020/11/24 19:58
 * @description TODO 消费者
 * <p>
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 */
@Component
@Slf4j
public class Kafka2Consumer implements InitializingBean {

    public String topic = KafkaConfig.topic;

    @Resource
    KafkaConfig kafkaConfig;

    @Override
    public void afterPropertiesSet() {
        //每个线程一个KafkaConsumer实例，且线程数设置成分区数，最大化提高消费能力
        int consumerThreadNum = 2;
        for (int i = 0; i < consumerThreadNum; i++) {
            new KafkaConsumerThread(kafkaConfig.customerConfigs(), topic)
                    .start();
        }
    }

    class KafkaConsumerThread extends Thread {

        private KafkaConsumer<String, String> kafkaConsumer;

        public KafkaConsumerThread(Properties props, String topic) {
            this.kafkaConsumer = new KafkaConsumer<>(props);
            this.kafkaConsumer.subscribe(Arrays.asList(topic));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("【**接收消息**】, offset:" + record.offset() + " , key:" + record.key() + " , value:" + record.value() + " , partition:" + record.partition());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                kafkaConsumer.close();
            }
        }
    }
}