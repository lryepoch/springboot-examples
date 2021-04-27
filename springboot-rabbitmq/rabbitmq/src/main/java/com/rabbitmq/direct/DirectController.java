package com.rabbitmq.direct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lryepoch
 * @date 2020/12/18 17:21
 * @description TODO 路由模式，生产者往队列中发送包含不同路由键的消息，instance 1获取到了orange和black消息，instance 2获取到了green和black消息。
 */
@Controller
@RequestMapping("/rabbit")
public class DirectController {

    @Autowired
    private DirectSender directSender;

    @RequestMapping(value = "/direct", method = RequestMethod.GET)
    @ResponseBody
    public Object directTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            directSender.send(i);
            Thread.sleep(1000);
        }
        return null;
    }
}
