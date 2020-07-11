package com.demo.client;

import com.demo.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/7/8 15:00
 * @description TODO
 */
//@FeignClient(value="PRODUCT-DATA-SERVICE")

/**
 * value --->指定调用哪个服务
 * fallbackFactory--->熔断器的降级提示
 *
 * 如果PRODUCT-DATA-SERVICE不可用，就调用ProductClientFeignHtstrix反馈信息
 */
@FeignClient(value = "PRODUCT-DATA-SERVICE", fallback = ProductClientFeignHystrix.class)
public interface ProductClientFeign {

    // 采用Feign我们可以使用SpringMVC的注解来对服务进行绑定！
    @GetMapping("/list")
    public List<Product> listProducts();
}
