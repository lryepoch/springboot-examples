package com.shiro.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lryepoch
 * @date 2020/5/19 17:02
 * @description TODO
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthenticatedException e){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ex",e);
        modelAndView.setViewName("unauthorized");
        return modelAndView;
    }
}
