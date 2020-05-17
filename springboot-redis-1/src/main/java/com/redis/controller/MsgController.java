package com.redis.controller;

import com.redis.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
public class MsgController {

    @Autowired
    private MsgService msgService;

    @RequestMapping("set")
    public String setMsg(@RequestParam(value = "key") String key, @RequestParam(value = "msg") String msg) {
        return msgService.setMsg(key,msg);
    }

    @RequestMapping("get")
    public String getMsg(@RequestParam(value = "key") String key){
        return msgService.getMsg(key);
    }
}
