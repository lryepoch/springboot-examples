package com.interceptor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("test")
    public String getUser(){
        logger.info("执行控制器----");
        return "ddd";
    }
}
