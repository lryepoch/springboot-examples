package com.demo;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author lryepoch
 * @date 2020/7/8 9:47
 * @description TODO 注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        //8761这个端口是默认的，不要修改，因为后面的子项目都会访问这个端口
        int port = 8761;
        if (!NetUtil.isUsableLocalPort(port)){
            System.err.printf("端口%d被占用了，无法启动%n",port);
            System.exit(1);
        }
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port="+port).run(args);
    }
}
