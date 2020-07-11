package com.demo.web;

import com.demo.pojo.Product;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 15:05
 * @description TODO 从 config-server 去获取 version 信息
 */
@Controller
@RefreshScope
public class ProductController {
    @Autowired
    ProductService productService;

    @Value("${version}")
    String version;

    @RequestMapping("/list")
    public Object products(Model model){
        List<Product> ps = productService.listProducts();
        model.addAttribute("version",version);
        model.addAttribute("ps",ps);
        return "products";
    }

}
