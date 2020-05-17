package com.interceptor.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器，配置拦截规则
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor1 myInterceptor1;

    @Autowired
    private MyInterceptor2 myInterceptor2;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * addInterceptor 注册拦截器
         * addPathPatterns  配置拦截规则
         */
        registry.addInterceptor(myInterceptor1)
                .addPathPatterns("/*")
                .excludePathPatterns("/test")
                .order(5);

        registry.addInterceptor(myInterceptor2)
                .addPathPatterns("/*")
                .order(3);
    }
}
