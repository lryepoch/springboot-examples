package com.rabbitmq.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lryepoch
 * @date 2020/12/18 17:26
 * @description TODO 通配符模式，生产者往队列中发送包含不同路由键的消息，instance 1和instance 2分别获取到了匹配的消息
 */
@Controller
@RequestMapping("/rabbit")
public class TopicController {

    @Autowired
    private TopicSender topicSender;

    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    @ResponseBody
    public Object topicTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            topicSender.send(i);
            Thread.sleep(1000);
        }
        return null;
    }
}
