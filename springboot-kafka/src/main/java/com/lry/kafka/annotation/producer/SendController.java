package com.lry.kafka.annotation.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
