package com.demo;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author lryepoch
 * @date 2020/7/9 13:57
 * @description TODO 为了方便监控集群里的多个实例，springCloud 提供了一个 turbine 项目，它的作用是把一个集群里的多个实例汇聚在一个 turbine 里，
 *                   然后再在 断路器监控 里查看这个 turbine, 这样就能够在集群层面进行监控
 */
@SpringBootApplication
@EnableTurbine
public class ProductServiceTurbineApplication {
    public static void main(String[] args) {
        int port = 8021;
        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n",port);
            System.exit(1);
        }
        new SpringApplicationBuilder(ProductServiceTurbineApplication.class).properties("server.port="+port).run(args);
    }
}
