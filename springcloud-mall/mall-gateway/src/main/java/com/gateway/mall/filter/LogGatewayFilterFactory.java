package com.gateway.mall.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author lryepoch
 * @date 2021/4/25 15:13
 * @description TODO 自定义局部过滤器
 *
 * 在Gateway中, Filter的生命周期只有两个，PRE 和 POST。
 * PRE这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 * POST这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 *
 * 过滤器分为局部过滤器(GatewayFilter) 和全局过滤器(GlobalFilter) ，
 * GatewayFilter主要是应用到单个路由或者一个分组的路由上，而GlobalFilter则是应用到所有的路由上。
 *
 * 此处，开启日志（控制台or缓存）
 *
 * http://localhost:8888/api/order/message1?age=20
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    //构造方法
    public LogGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 读取配置文件中的参数，赋值到配置类中
     * @return
     */
    public List<String> shortcutFieldOrder(){
        return Arrays.asList("consoleLog", "cacheLog");
    }

    /**
    * 过滤器逻辑
    */
    @Override
    public GatewayFilter apply(LogGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if(config.isConsoleLog()){
                    System.out.println("consoleLog已经开启了……");
                }
                if(config.isCacheLog()){
                    System.out.println("cacheLog已经开启了……");
                }

                return chain.filter(exchange);
            }
        };
    }

    /**
     * 配置类，接收配置参数
     */
    public static class Config {
        private boolean consoleLog;
        private boolean cacheLog;

        public boolean isConsoleLog() {
            return consoleLog;
        }

        public void setConsoleLog(boolean consoleLog) {
            this.consoleLog = consoleLog;
        }

        public boolean isCacheLog() {
            return cacheLog;
        }

        public void setCacheLog(boolean cacheLog) {
            this.cacheLog = cacheLog;
        }
    }
}
