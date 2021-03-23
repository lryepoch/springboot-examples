package com.order.mall.controller;

import com.common.mall.domain.Order;
import com.common.mall.domain.Product;
import com.order.mall.feign.ProductService;
import com.order.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author lryepoch
 * @date 2021/3/22 12:23
 * @description TODO
 */
@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    //下单
    @RequestMapping("/order/prod/{pid}")
    public Order createOrder(@PathVariable("pid") Integer pid) {

        //调用商品微服务,查询商品信息
//        Product product = restTemplate.getForObject("http://localhost:10010/product/" + pid, Product.class);

        Product product = productService.findById(pid);

        //下单(创建订单)
        Order order = new Order();
        order.setUid(1);
        order.setUsername("测试用户");
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderService.createOrder(order);
        return order;
    }
}
