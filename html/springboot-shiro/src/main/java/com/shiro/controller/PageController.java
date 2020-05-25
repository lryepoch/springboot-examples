package com.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lryepoch
 * @date 2020/5/19 15:26
 * @description TODO 专门显示页面的控制器
 */
@Controller
public class PageController {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("unauthorized")
    public String noPerms() {
        return "unauthorized";
    }

//    @RequiresPermissions("deleteOrder")
    @RequestMapping("deleteOrder")
    public String deleteOrder(){
        return "deleteOrder";
    }

//    @RequiresPermissions("deleteProduct")
    @RequestMapping("deleteProduct")
    public String deleteProduct(){
        return "deleteProduct";
    }

//    @RequiresPermissions("listProduct")
    @RequestMapping("listProduct")
    public String listProduct(){
        return "listProduct";
    }

}
