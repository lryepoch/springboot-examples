package com.demo.web;

import com.demo.pojo.Product;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 15:05
 * @description TODO
 */
@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/list")
    public Object products(Model model){
        List<Product> ps = productService.listProducts();
        model.addAttribute("ps",ps);
        return "products";
    }

}
