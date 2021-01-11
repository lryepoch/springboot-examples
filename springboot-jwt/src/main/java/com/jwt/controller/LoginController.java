package com.jwt.controller;


import com.jwt.config.JWTUtil;
import com.jwt.config.ShiroKit;
import com.jwt.config.model.BaseResponse;
import com.jwt.entity.vo.LoginParam;
import com.jwt.entity.vo.ManagerInfo;
import com.jwt.service.ManagerInfoService;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录接口类
 */
@RestController
public class LoginController {

    @Resource
    private ManagerInfoService managerInfoService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginParam loginParam) {
        logger.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        ManagerInfo user = managerInfoService.findByUsername(username);
        //随机数盐
        String salt = user.getSalt();
        //原密码加密（通过username + salt作为盐）
        String encodedPassword = ShiroKit.md5(password, username + salt);
        logger.info("用户输入密码："+password);
        logger.info("username + salt作为盐: "+username + salt);
        logger.info("加密后密码: "+encodedPassword);
        if (user.getPassword().equals(encodedPassword)) {
            logger.info("正常，则返回token");
            return new BaseResponse<>(true, "Login success", JWTUtil.sign(username, encodedPassword));
        } else {
            logger.info("不正常，则抛出异常");
            throw new UnauthorizedException();
        }
    }

}
