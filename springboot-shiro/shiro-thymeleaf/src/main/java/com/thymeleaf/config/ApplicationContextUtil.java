package com.thymeleaf.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2021/1/11 16:12
 * @description TODO spring 可以使用xml、注解的形式装配Bean，虽然这种方法很简单方便，但是有些特殊场景用不了。
 *                   spring加载配置文件时，会自动调用ApplicationContextAware中的setApplicationContext。
 *                   我们可以实现一个工具类继承ApplicationContextAware 并重写setApplicationContext 获取applicationContext 保存到工具类中，通过
 *                   注解@Component 或其他 将工具类bean交给spring管理创建。这以后我们就能够方便在我们想要的地方通过applicationContext 获取想要的bean了。
 *
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取Bean
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    //通过class获取Bean
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }
}
