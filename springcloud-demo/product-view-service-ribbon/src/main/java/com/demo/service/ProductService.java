package com.demo.service;

import com.demo.client.ProductClientRibbon;
import com.demo.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 15:03
 * @description TODO 服务类，数据从 ProductClientRibbon 中获取
 */
@Service
public class ProductService {
    @Autowired
    ProductClientRibbon productClientRibbon;

    public List<Product> listProducts(){
        return productClientRibbon.listProducts();
    }

}
