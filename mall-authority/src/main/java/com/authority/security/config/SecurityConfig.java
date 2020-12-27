package com.authority.security.config;

import com.authority.security.component.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 17:49
 * @Description 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        //不需要保护的资源路径允许访问
        for (String url : ignoreUrlConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

    }

    @Bean
    private IgnoreUrlsConfig ignoreUrlConfig() {
        return new IgnoreUrlsConfig();
    }
}
