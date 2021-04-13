package com.gateway.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;

/**
 * @author lryepoch
 * @date 2021/3/24 12:16
 * @description TODO 直接和nacos配合使用，实现路由的负载均衡效果，所以需要在启动类上添加服务发现注解@EnableDiscoveryClient，
 *                   同时去除数据源的加载，避免报错，因为目前来说，网关暂不需要加载数据源
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class MallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGatewayApplication.class, args);
    }
}
