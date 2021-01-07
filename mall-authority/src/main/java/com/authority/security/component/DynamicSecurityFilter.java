package com.authority.security.component;

import com.authority.security.config.IgnoreUrlsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author lryepoch
 * @date 2020/12/28 16:12
 * @description TODO 动态权限过滤器，用于实现基于路径的动态权限过滤(请求经过：2) 。过滤掉不需拦截的资源权限
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSecurityFilter.class);

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            logger.info("2.OPTIONS请求直接放行:{}", request.getMethod());
            return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                logger.info("2.白名单请求直接放行: {}", path);
                return;
            }
        }

        /**
         * beforeInvocation() 方法实现了对访问受保护对象的权限校验，它会从SecurityContextHolder获取Authentication，
         * 然后通过 SecurityMetadataSource 可以得知当前请求是否在请求受保护的资源。对于请求那些受保护的资源，
         * 如果 Authentication.isAuthenticated() 返回false或者 AbstractSecurityInterceptor 的 alwaysReauthenticate属性为true，
         * 那么将会使用其引用的 AuthenticationManager 再认证一次。
         * 然后就是利用 AccessDecisionManager 进行权限的检查，调用 AccessDecisionManager 中的decide方法进行鉴权操作。
         */
        logger.info("2.执行super.beforeInvocation(fi)前^");
        InterceptorStatusToken token = super.beforeInvocation(fi);
        logger.info("2.执行super.beforeInvocation(fi)后$，token是: {}", token);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            //afterInvocation()方法实现了对返回结果的处理，在注入了AfterInvocationManager的情况下默认会调用其decide()方法。
            logger.info("2.super.afterInvocation(token, null)前^");
            super.afterInvocation(token, null);
            logger.info("2.super.afterInvocation(token, null)后$");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * 交替与类DynamicSecurityMetadataSource通讯
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}