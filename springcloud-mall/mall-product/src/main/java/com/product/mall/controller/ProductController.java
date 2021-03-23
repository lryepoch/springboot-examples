package com.product.mall.controller;

import com.common.mall.domain.Product;
import com.product.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/3/22 11:46
 * @description TODO
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${server.port}")
    private String port;

    //商品信息查询
    @RequestMapping("/product/{pid}")
    public Product findByPid(@PathVariable("pid") Integer pid) {
        Product product = productService.findByPid(pid);
        System.out.println("实例被调用，端口为：" + port);
        return product;
    }

}
