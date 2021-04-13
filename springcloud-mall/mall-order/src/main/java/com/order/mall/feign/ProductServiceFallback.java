package com.order.mall.feign;

import com.product.mall.domain.Product;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/3/24 8:36
 * @description TODO 为下单接口做容错降级，保证订单服务高可用，下单接口会涉及到商品服务，所以需要对查询商品信息的远程接口做容错
 *                   重写的方法可以理解成是原方法对应的降级方法，一旦下游服务出现问题，这些方法就是兜底方法
 *
 *
 */
@Component
public class ProductServiceFallback implements ProductService {
    public Product findById(Integer pid) {
        Product product = new Product();
        product.setPid(-1);
        return product;
    }
}
