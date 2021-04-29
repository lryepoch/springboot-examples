package com.ttl;

import com.ttl.config.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/4/29 10:43
 * @description TODO 第一种延迟队列，死信队列
 */
@SpringBootApplication
@RestController
public class TtlMqApplication {

    @Autowired
    private Publisher publisher;

    @GetMapping(value = "/init")
    public void init(){
        publisher.publish("Hello World！");
    }

    public static void main(String[] args){
        SpringApplication.run(TtlMqApplication.class, args);
    }
}
