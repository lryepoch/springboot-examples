package com.demo.service;

import com.demo.pojo.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 11:37
 * @description TODO
 */
@Service
public class ProductService {
    @Value("${server.port}")
    String port;

    public List<Product> list() {
        List<Product> ps = new ArrayList<>();
        ps.add(new Product(1, "a:" + port, 50));
        ps.add(new Product(2, "b:" + port, 150));
        ps.add(new Product(3, "c:" + port, 250));
        return ps;
    }
}
