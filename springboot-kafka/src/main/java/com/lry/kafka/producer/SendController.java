package com.lry.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    private Producer producer;

    @RequestMapping("send")
    public String send(){
        producer.send();
        return "run successfully";
    }
}
