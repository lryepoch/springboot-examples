package com.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/unauthorized")
    public String noPerms() {
        return "unauthorized";
    }

//    @RequiresRoles("admin")     //这个注解表示：该方法需要admin这个角色才能访问
    @GetMapping("/deleteOrder")
    public String deleteOrder(){
        return "deleteOrder";
    }

//    @RequiresRoles(value={"admin","productManager"}, logical = Logical.OR)  //这个注解表示：该方法需要"admin","productManager"这个角色才能访问
    @GetMapping("/deleteProduct")
    public String deleteProduct(){
        return "deleteProduct";
    }

//    @RequiresRoles(value={"admin","productManager"}, logical = Logical.OR)  //这个注解表示：该方法需要"admin","productManager"这个角色才能访问
    @GetMapping("/listProduct")
    public String listProduct(){
        return "listProduct";
    }

}
