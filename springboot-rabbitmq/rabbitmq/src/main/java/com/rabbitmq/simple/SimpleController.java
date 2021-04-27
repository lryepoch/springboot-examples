package com.rabbitmq.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lryepoch
 * @date 2020/12/18 16:32
 * @description TODO 简单模式，生产者往队列中发送消息，消费者从队列中获取消息并消费
 */
@Controller
@RequestMapping("/rabbit")
public class SimpleController {

    @Autowired
    private SimpleSender simpleSender;

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public Object simpleTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            simpleSender.send(i);
            Thread.sleep(1000);
        }
        return null;
    }
}
