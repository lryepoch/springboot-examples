package com.demo.client;

import com.demo.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/9 11:25
 * @description TODO 断路器配置
 */
@Component
public class ProductClientFeignHystrix implements ProductClientFeign {
    @Override
    public List<Product> listProducts() {
        List<Product> result = new ArrayList<>();
        result.add(new Product(0,"产品数据微服务不可用",0));
        return result;
    }
}
