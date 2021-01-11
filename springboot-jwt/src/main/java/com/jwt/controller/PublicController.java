package com.jwt.controller;

import com.jwt.config.ShiroKit;
import com.jwt.config.model.BaseResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 机具管理API接口类
 */
@RestController
@RequestMapping(value = "/api/v1")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    /**
     * 入网绑定查询接口
     *
     * @return 是否入网
     *
     * @RequiresAuthentication 验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时。
     */
    @GetMapping(value = "/join")
//    @RequiresAuthentication
    public BaseResponse join() {
        if (ShiroKit.notAuthenticated()){
            throw new UnauthorizedException();
        }
        logger.info("入网查询接口……");
        BaseResponse result = new BaseResponse();
        result.setSuccess(true);
        result.setMsg("已入网并绑定了网点");
        return result;
    }
}
