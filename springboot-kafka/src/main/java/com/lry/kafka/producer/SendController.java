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
        for (int i=0;i<100;i++){
            producer.send();
        }
        return "run successfully";
    }
}
