package com.lry.kafka.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/1/25 11:14
 * @description TODO
 */
@RestController
public class ClientController {
    @Autowired
    private Kafka2Producer producer;

    @RequestMapping("/sender")
    public String send() {
        for (int i = 0; i < 100; i++) {
            producer.producer(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "send successfully";
    }
}
