package com.order.mall.controller;

import com.order.mall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/3/23 15:35
 * @description TODO 微服务接入Sentinel
 */
@RestController
public class TestController {

//    @GetMapping("/order/message1")
//    public String message1() {
//        return "message1";
//    }
//
//    @GetMapping("/order/message2")
//    public String message2() {
//        return "message2";
//    }

    /**
     * 流控规则->链路规则；
     * <p>
     * 让两个测试方法都调用TestService提供的方法
     */
    @Autowired
    private TestService testService;

    @GetMapping("/order/message1")
    public String message1() {
        testService.message();
        return "message1";
    }

    @GetMapping("/order/message2")
    public String message2() {
        testService.message();
        return "message2";
    }

    /**
     * 降级规则
     */
    @GetMapping("/order/message3")
    public String message3() {
        int i = 1;
        //异常比例为0.333
        if (i % 3 == 0) {
            throw new RuntimeException();
        }
        return "message3";
    }

}
