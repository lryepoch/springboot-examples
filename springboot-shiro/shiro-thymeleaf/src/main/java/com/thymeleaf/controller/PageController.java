package com.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lryepoch
 * @date 2020/5/19 15:26
 * @description TODO 专门显示页面的控制器
 */
@Controller
public class PageController {

    @GetMapping(value = "/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/index")
    public String index() {
        return "indexPage";
    }

    @GetMapping("/unauthorized")
    public String noPerms() {
        return "unauthorizedPage";
    }

//    @RequiresRoles("admin")     //这个注解表示：该方法需要admin这个角色才能访问
    @GetMapping("/deleteOrder")
    public String deleteOrder(){
        return "deleteOrderPage";
    }

//    @RequiresRoles(value={"admin","productManager"}, logical = Logical.OR)  //这个注解表示：该方法需要"admin","productManager"这个角色才能访问
    @GetMapping("/deleteProduct")
    public String deleteProduct(){
        return "deleteProductPage";
    }

//    @RequiresRoles(value={"admin","productManager"}, logical = Logical.OR)  //这个注解表示：该方法需要"admin","productManager"这个角色才能访问
//    @RequiresPermissions(value = {"listProduct"})
    @GetMapping("/listProduct")
    public String listProduct(){
        return "listProductPage";
    }

}
