package com.x;

import com.x.controller.RabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/4/27 11:48
 * @description TODO
 */
@SpringBootApplication
@RestController
public class RabbitApplication {

    @Autowired
    private RabbitProducer producer;

    @GetMapping("/init")
    public void init(){
        String message1 = "这是第一条消息";
        String message2 = "这是第二条消息";
        producer.sendDelayMessage(message1, 5000);
        producer.sendDelayMessage(message2, 10000);
    }

    public static void main(String[] args){
        SpringApplication.run(RabbitApplication.class, args);
    }
}
