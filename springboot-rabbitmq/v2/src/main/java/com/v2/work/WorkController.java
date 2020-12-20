package com.v2.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lryepoch
 * @date 2020/12/18 16:32
 * @description TODO 工作模式，生产者往队列中发送包含不同数量.号的消息，instance 1和instance 2消费者互相竞争，分别消费了一部分消息
 */
@Controller
@RequestMapping("/rabbit")
public class WorkController {

    @Autowired
    private WorkSender workSender;

    @ResponseBody
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public Object simpleTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            workSender.send(i);
            Thread.sleep(1000);
        }
        return null;
    }
}
