package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2020/5/19 14:49
 * @description TODO
 */
@RestController
public class LoginController {

    @RequestMapping("login")
    public String login(String name,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);

        subject.login(token);
        Session session = subject.getSession();
        session.setAttribute("subject",subject);
        return "redirect:index";

    }

}
