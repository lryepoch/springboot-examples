package com.lry.kafka.gj;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static com.lry.kafka.gj.SenderController.TOPIC;

/**
 * @author lryepoch
 * @date 2021/1/25 10:20
 * @description TODO 消费者。单例模式实现kakfa。
 */
public class KafkaInnerConsumer {
    //饿汉式
    public static KafkaInnerConsumer instance = new KafkaInnerConsumer();

    public static KafkaInnerConsumer getInstance(){
        return instance;
    }

    public static KafkaConsumer consumer = null;

    static {
//        System.setProperty("java.security.auth.login.config", "E:\\IdeaProjects\\springboot-kafka\\src\\main\\kerberos\\jaas.conf");
//        System.setProperty("java.security.krb5.conf", "E:\\IdeaProjects\\springboot-kafka\\src\\main\\kerberos\\krb5.conf");

        Properties props = new Properties();
        try {
            props.load(instance.getClass().getResourceAsStream("/consumer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        consumer = new KafkaConsumer(props);
    }

    public KafkaConsumer getConsumer(){
        return consumer;
    }

    public static void main(String[] arg) {
        consumer.subscribe(Arrays.asList(TOPIC));
        //消费并打印消费结果
        while (true) {
            //消费者一次poll100条新消息，并且提交偏移量
            ConsumerRecords<String, Integer> records = consumer.poll(100);
            for (ConsumerRecord record : records) {
                System.out.printf("offset = %d, key = %s, value= %s%n", record.offset(), record.key(), record.value());
            }
        }
    }
}
