package com.gateway.mall.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author lryepoch
 * @date 2021/4/25 15:28
 * @description TODO 自定义全局过滤器只需要实现GlobalFilter和Ordered接口，然后重写里面的filter方法和getOrder方法
 *
 * 此处，简单实现一个全局过滤器，去校验所有请求的请求参数中是否包含“token”，如果不包含请求参数“token”则不转发路由，否则执行正常的逻辑。
 *
 * http://localhost:8888/api/order/message1?age=20&token=admin
 *
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    /**
    * 全局过滤器判断逻辑
    */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(!StringUtils.equals(token, "admin")){
            System.out.println("鉴权失败");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //调用chain.filter继续向下游执行
        System.out.println("鉴权成功");
        return chain.filter(exchange);
    }

    /**
    * 顺序，数值越小，优先级越高
    */
    @Override
    public int getOrder() {
        return 0;
    }
}
