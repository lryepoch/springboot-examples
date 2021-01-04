package com.authority.security.config;

import com.authority.security.component.*;
import com.authority.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 17:49
 * @Description 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        // 禁用缓存
//        httpSecurity.headers().cacheControl();

        //不需要保护的资源路径允许访问
        for (String url : ignoreUrlConfig().getUrls()) {
            registry.antMatchers(url)
                    .permitAll();
        }

        //允许跨域请求的OPTIONS请求。//跨域请求会先进行一次OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();

        //其余任何请求需要身份认证
        registry.and()
                .authorizeRequests()
                //除上面外的所有请求全部需要鉴权认证
                .anyRequest()
                .authenticated()
                //关闭跨站请求防护及不使用session
                .and()
                //由于使用的是JWT，我们这里不需要CSRF
                .csrf()
                .disable()
                //基于token，所以不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //自定义权限拒绝处理类，（添加自定义未授权和未登录结果返回）
                .and()
                .exceptionHandling()
                //无权限
                .accessDeniedHandler(restfulAccessDeniedHandler())
                //未登录
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                //自定义权限拦截器JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);   //添加JWT filter
        //有动态权限配置时添加动态权限校验过滤器
        if (dynamicSecurityService != null) {
            registry.and()
                    .addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    /**
     * @author lryepoch
     * @date 2021/1/4 11:57
     * @description JWT登录授权过滤器
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @author lryepoch
     * @date 2021/1/4 11:56
     * @description 配置白名单资源路径
     */
    @Bean
    public IgnoreUrlsConfig ignoreUrlConfig() {
        return new IgnoreUrlsConfig();
    }

    /**
     * @author lryepoch
     * @date 2021/1/4 11:56
     * @description JwtToken生成的工具类
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
    
//    @ConditionalOnBean         //	当给定的在bean存在时，则实例化当前Bean
//    @ConditionalOnMissingBean  //	当给定的在bean不存在时，则实例化当前Bean
//    @ConditionalOnClass        //	当给定的类名在类路径上存在，则实例化当前Bean
//    @ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则实例化当前Bean
    /**
     * @author lryepoch
     * @date 2021/1/4 11:54
     * @description 判断用户是否有访问权限
     */
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    /**
     * @author lryepoch
     * @date 2021/1/4 11:54
     * @description 实现基于路径的动态权限过滤
     */
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

    /**
     * @author lryepoch
     * @date 2021/1/4 11:55
     * @description 动态权限数据源，用于获取动态权限规则
     */
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }
}
