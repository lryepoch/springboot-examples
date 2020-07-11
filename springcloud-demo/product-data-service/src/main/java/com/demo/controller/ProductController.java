package com.demo.controller;

import com.demo.pojo.Product;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 11:32
 * @description TODO
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public Object products(){
        List<Product> ps = productService.list();
        return ps;
    }
}
