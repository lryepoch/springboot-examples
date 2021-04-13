package com.product.mall.service;


import com.product.mall.domain.Product;

/**
 * @author lryepoch
 * @date 2021/3/22 11:47
 * @description TODO
 */
public interface ProductService {

    //根据pid查询商品信息
    Product findByPid(Integer pid);
}
