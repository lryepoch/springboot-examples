package com.shiro.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/20 19:13
 * @description TODO 通过ApplicationContextAware，Spring容器会自动把上下文环境对象调用ApplicationContextAware接口中的setApplicationContext方法,
 *                   可以通过这个上下文环境对象得到Spring容器中的Bean
 *
 *                   ApplicationContextAware，其实我们看到---Aware就知道是干嘛用的了，就是属性注入的，
 *                   但是这个ApplicationContextAware的不同地方在于，实现了这个接口的bean，
 *                   当spring容器初始化的时候，会自动的将ApplicationContext注入进来
 *
 *                   因为URLPathMatchingFilter 没有被声明为@Bean, 那么换句话说 URLPathMatchingFilter 就没有被Spring管理起来，
 *                   那么也就无法在里面注入 PermissionService类了。
 *                   但是在业务上URLPathMatchingFilter 里面又必须使用PermissionService类，怎么办呢? 就借助SpringContextUtils 这个工具类，
 *                   来获取PermissionService的实例。
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) throws BeansException{
        SpringContextUtils.context = context;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
