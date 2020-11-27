package com.async.controller;


import com.async.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AsyncController {

    @Resource
    private AsyncService asyncService;

    @GetMapping(value = "/sayHello")
    public String sayHello() {
        Long startDate = System.currentTimeMillis();
        asyncService.hello();
        Long endDate = System.currentTimeMillis();

        return "数据处理结束  耗时：" + (endDate - startDate) / 1000 + "秒";
    }
}
