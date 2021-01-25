package com.lry.kafka.gj;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static com.lry.kafka.gj.SenderController.TOPIC;

/**
 * @author lryepoch
 * @date 2021/1/25 10:21
 * @description TODO 接收消息。单例模式实现kakfa。
 */
@RestController
@Slf4j
public class ReceiverController {

    @GetMapping(value = "/receive")
    public void receive() throws InterruptedException {
        KafkaConsumer consumer = KafkaInnerConsumer.getInstance().getConsumer();
        consumer.subscribe(Arrays.asList(TOPIC));
        //消费并打印消费结果
        while (true) {
            ConsumerRecords<String, Integer> records = consumer.poll(100);
            for (ConsumerRecord record : records) {
                log.info("【**接收消息**】, offset:" + record.offset() + " , key:" + record.key() + " , value:" + record.value() + " , partition:" + record.partition());
            }
            Thread.sleep(1000);
        }

    }
}
