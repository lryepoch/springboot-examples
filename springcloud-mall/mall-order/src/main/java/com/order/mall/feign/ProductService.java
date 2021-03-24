package com.order.mall.feign;

import com.common.mall.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lryepoch
 * @date 2021/3/23 10:35
 * @description TODO 引入Fegin组件，即简化了远程调用的编码逻辑，又完成了负载均衡的要求。
 *                   Feign是Spring Cloud提供的一个声明式的伪Http客户端， 它使得调用远程服务就像调用本地服务一样简单，
 *                   只需要创建一个接口并添加一个注解即可，主要是它还默认集成了Ribbon ，所以在Nacos下使用Fegin默认就可以实现负载均衡的效果。
 */
//声明调用的提供者的name,这个name就是我们配置文件里设置的application.name
//@FeignClient(value = "mall-product", fallback = ProductServiceFallback.class)
@FeignClient(value = "mall-product", fallbackFactory = ProductServiceFallbackFactory.class)
public interface ProductService {

    @GetMapping("/product/{pid}")
    Product findById(@PathVariable("pid") Integer pid);
}
