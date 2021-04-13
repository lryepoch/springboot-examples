package com.product.mall.service.impl;

import com.product.mall.dao.ProductDao;
import com.product.mall.domain.Product;
import com.product.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lryepoch
 * @date 2021/3/22 11:47
 * @description TODO
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    public Product findByPid(Integer pid) {
        return productDao.selectById(pid);
    }
}
