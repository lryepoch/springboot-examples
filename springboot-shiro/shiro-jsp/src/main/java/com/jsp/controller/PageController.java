package com.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lryepoch
 * @date 2020/5/19 15:26
 * @description TODO 专门显示页面的控制器
 */
@Controller
public class PageController {

    /**
    * 登录
    */
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
    * 首页
    */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
    * 未授权
    */
    @GetMapping("/unauthorized")
    public String noPerms() {
        return "unauthorized";
    }

    /**
    * 删除订单
    */
    @GetMapping("/deleteOrder")
    public String deleteOrder(){
        return "deleteOrder";
    }

    /**
    * 删除产品
    */
    @GetMapping("/deleteProduct")
    public String deleteProduct(){
        return "deleteProduct";
    }

    /**
    * 查看产品
    */
    @GetMapping("/listProduct")
    public String listProduct(){
        return "listProduct";
    }

}
