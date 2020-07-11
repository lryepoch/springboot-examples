package com.demo.service;

import com.demo.client.ProductClientFeign;
import com.demo.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 15:03
 * @description TODO
 */
@Service
public class ProductService {
    @Autowired
    ProductClientFeign productClientFeign;

    public List<Product> listProducts(){
        return productClientFeign.listProducts();
    }

}
