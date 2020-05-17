package com.interceptor.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建拦截器类1
 * @Component 和 @Service 是一个作用 创建对象
 */
@Component
public class MyInterceptor1 implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(MyInterceptor1.class);

    /**
     * 在请求到达Controller控制器之前，通过拦截器执行一段代码
     * 如果方法返回true，继续执行后续操作
     * 如果返回false，执行中断请求处理，请求不会发送到Controller
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截器1 在控制器执行之前执行");
        return true;
    }

    /**
     * 执行控制器之后，跳转前
     *
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("拦截器1 在控制器执行之后执行");
    }

    /**
     * 跳转之后执行
     *
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("拦截器1 最后执行");
    }
}
