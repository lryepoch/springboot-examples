package com.lry.kafka.annotation.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description  第一种方法：基于注解实现
* @Author       lryepoch
* @CreateDate   2020/11/24 23:46
*/
@RestController
public class SendController {
    @Autowired
    private Producer producer;

    @GetMapping(value = "/send")
    public String send() {
        for (int i = 0; i < 100; i++) {
            producer.send();
        }
        return "run successfully";
    }
}
