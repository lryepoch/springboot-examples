package com.jwt.controller;

import com.auth0.jwt.JWT;
import com.jwt.config.util.JWTUtil;
import com.jwt.config.util.ShiroKit;
import com.jwt.config.model.BaseResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 机具管理API接口类
 */
@RestController
@RequestMapping(value = "/api")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    /**
     * 入网绑定查询接口
     *
     * @return 是否入网
     */
    @GetMapping(value = "/join")
//    //做法一：@RequiresAuthentication 验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时
//    @RequiresAuthentication
    public BaseResponse join(HttpServletRequest request) {
        //做法二：
        if (ShiroKit.notAuthenticated()) {
            throw new UnauthorizedException();
        }
        logger.info("登录认证通过啦……");

        String token = request.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        logger.info("authorization：{}", token);
        logger.info("username：{}", username);

        BaseResponse result = new BaseResponse();
        result.setSuccess(true);
        result.setMsg("已入网并绑定了网点");
        return result;
    }
}
