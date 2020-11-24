package com.lry.kafka.client;

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
 */
@Component
public class Kafka_Consumer implements InitializingBean {

    public String topic = KafkaConfig.topic;

    @Resource
    KafkaConfig kafkaConfig;

    @Override
    public void afterPropertiesSet() {
        //每个线程一个KafkaConsumer实例，且线程数设置成分区数，最大化提高消费能力
        int consumerThreadNum = 2;
        for (int i = 0; i < consumerThreadNum; i++) {
            new KafkaConsumerThread(kafkaConfig.customerConfigs(), topic).start();
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
                        System.out.println("message------------" + record.value());
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