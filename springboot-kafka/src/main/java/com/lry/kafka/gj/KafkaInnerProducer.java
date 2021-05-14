package com.lry.kafka.gj;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

/**
 * @author lryepoch
 * @date 2021/1/25 10:20
 * @description TODO 生产者。单例模式实现kakfa。
 */
public class KafkaInnerProducer {
    /*----------1--------*/
//    public static void main(String[] args) throws InterruptedException {
//        Properties props = new Properties();
//        try {
//            props.load(MsgProducer.class.getResourceAsStream("/producer.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //读取配置文件
//        Producer<String, String> producer = new KafkaProducer<>(props);
//        for (int i = 0; i < 100; i++) {
//            String msg = "---------" + i + "--------";
//            producer.send(new ProducerRecord<>("lytest", i + "", msg));
//            System.out.println("生产数据 key：value -> " + i + "：" + msg);
//            Thread.sleep(1000);
//        }
//        producer.close();
//    }

    /*-------2.单例模式-----*/
    //懒汉式
    private volatile static KafkaInnerProducer instance;

    private KafkaInnerProducer() {
    }

    public static KafkaInnerProducer getInstance() {
        synchronized (KafkaInnerProducer.class) {
            if (instance == null) {
                instance = new KafkaInnerProducer();
                instance.init();
            }
        }
        return instance;
    }

    public static KafkaProducer producer=null;

    public void init() {
//        System.setProperty("java.security.auth.login.config", "E:\\IdeaProjects\\springboot-kafka\\src\\main\\kerberos\\jaas.conf");
//        System.setProperty("java.security.krb5.conf", "E:\\IdeaProjects\\springboot-kafka\\src\\main\\kerberos\\krb5.conf");

        Properties props = new Properties();
        try {
            props.load(instance.getClass().getResourceAsStream("/producer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer(props);
    }

    public void sendMessage(String topic, String key, String message) {
        producer.send(new ProducerRecord(topic, key, message));
        producer.flush();
    }
}