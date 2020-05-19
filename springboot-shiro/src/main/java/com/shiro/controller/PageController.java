package com.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2020/5/19 15:26
 * @description TODO 专门显示页面的控制器
 */
@RestController
public class PageController {

    @RequiresPermissions("deleteOrder")
    @RequestMapping("deleteOrder")
    public String deleteOrder(){
        return "deleteOrder";
    }

    @RequiresPermissions("deleteProduct")
    @RequestMapping("deleteProduct")
    public String deleteProduct(){
        return "deleteProduct";
    }

    @RequiresPermissions("listProduct")
    @RequestMapping("listProduct")
    public String listProduct(){
        return "listProduct";
    }


}
