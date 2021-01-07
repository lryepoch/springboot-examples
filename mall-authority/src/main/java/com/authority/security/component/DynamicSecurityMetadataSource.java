package com.authority.security.component;

import cn.hutool.core.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author lryepoch
 * @date 2020/12/26 14:20
 * @description TODO 动态权限数据源，用于获取动态权限规则(请求经过：3) 。将当前请求的资源加入map中
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSecurityMetadataSource.class);

    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    /**
     * 获取某个受保护的安全对象object的所需要的权限信息, 是一组ConfigAttribute对象的集合，如果该安全对象object
     * 不被当前SecurityMetadataSource对象支持,则抛出异常IllegalArgumentException。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //查询所有的资源resource
        if (configAttributeMap == null) {
            this.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();
        String path = URLUtil.getPath(url);
        logger.info("3.url：{}", url);
        logger.info("3.path：{}", path);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            logger.info("3.pattern：{}", pattern);
            if (pathMatcher.match(pattern, path)) {
                logger.info("3.configAttributeMap.get(pattern)：{}", configAttributeMap.get(pattern));
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        //未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * getAttributes(Object o)方法通常配合boolean supports(Class<?> clazz)一起使用，先使用boolean supports(Class<?> clazz)确保安全对象
     * 能被当前SecurityMetadataSource支持，然后再调用该方法。
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}