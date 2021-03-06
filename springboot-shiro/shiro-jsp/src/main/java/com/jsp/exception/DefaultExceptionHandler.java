package com.jsp.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lryepoch
 * @date 2020/5/19 17:02
 * @description TODO 拦截没有权限的url，跳转到unauthorized.jsp页面
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(UnauthorizedException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ex", exception);
        modelAndView.setViewName("unauthorized");
        return modelAndView;
    }
}
