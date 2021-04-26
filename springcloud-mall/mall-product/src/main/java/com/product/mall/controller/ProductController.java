package com.product.mall.controller;

import com.product.mall.domain.Product;
import com.product.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2021/3/22 11:46
 * @description TODO 商品服务
 */
@RestController
@RefreshScope   //在需要动态读取配置的类上添加此注解就可以动态刷新功能。//nacos配置中心
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${server.port}")
    private String port;

    @Value("${product.name}")
    private String name;

    //商品信息查询
    @RequestMapping("/product/{pid}")
    public Product findByPid(@PathVariable("pid") Integer pid) {
        Product product = productService.findByPid(pid);
        System.out.println("product实例被调用，端口为：" + port);
        return product;
    }

    /**
    * 引入Nacos作为配置中心测试方法
    */
    @GetMapping("/get")
    public String get(){
        return name;
    }
}
