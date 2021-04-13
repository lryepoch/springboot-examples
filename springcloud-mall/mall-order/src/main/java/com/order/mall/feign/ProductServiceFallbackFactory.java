package com.order.mall.feign;

import com.product.mall.domain.Product;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/3/24 8:48
 * @description TODO 在容错类中拿到具体的错误 ，方便之后的问题排查。实现FallbackFactory能够看到异常，再跟踪下堆栈信息，排查问题就会快很多
 */
@Component
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService> {
    public ProductService create(final Throwable throwable) {
        return new ProductService() {
            public Product findById(Integer pid) {
                throwable.printStackTrace();
                Product product = new Product();
                product.setPid(-1);
                return product;
            }
        };
    }
}
