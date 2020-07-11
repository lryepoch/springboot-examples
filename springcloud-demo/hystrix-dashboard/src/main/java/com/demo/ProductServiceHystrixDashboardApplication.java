package com.demo;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author lryepoch
 * @date 2020/7/9 11:40
 * @description TODO 断路器监控启动类，主要就是@EnableHystrixDashboard 这个
 */
@SpringBootApplication
@EnableHystrixDashboard
public class ProductServiceHystrixDashboardApplication {
    public static void main(String[] args) {
        int port = 8020;
        if(!NetUtil.isUsableLocalPort(port)){
            System.err.printf("端口%d被占用了，无法启动%n",port);
            System.exit(1);
        }
        new SpringApplicationBuilder(ProductServiceHystrixDashboardApplication.class).properties("server.port="+port).run(args);
    }
}
