package com.thymeleaf.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/5/20 19:13
 * @description TODO 编写工具类SpringContextUtils以实例化UserService、PermissionService等Bean
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtils.context = context;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
