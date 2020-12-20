package com.v2.fanout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lryepoch
 * @date 2020/12/18 17:12
 * @description TODO 发布/订阅模式，生产者往队列中发送包含不同数量.号的消息，instance 1和instance 2同时获取并消费了消息
 */
@Controller
@RequestMapping("/rabbit")
public class FanoutController {

    @Autowired
    private FanoutSender fanoutSender;

    @RequestMapping(value = "/fanout", method = RequestMethod.GET)
    @ResponseBody
    public Object fanoutTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            fanoutSender.send(i);
            Thread.sleep(1000);
        }
        return null;
    }
}
