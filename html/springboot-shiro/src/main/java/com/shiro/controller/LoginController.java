package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author lryepoch
 * @date 2020/5/19 14:49
 * @description TODO 登录认证
 */
@Controller
public class LoginController {

    @PostMapping("/login")
    public String login(Model model, String name, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            //登录认证
            subject.login(token);

            Session session = subject.getSession();
            session.setAttribute("subject", subject);

            //授权方式：编程式。这是RBAC，基于角色的访问权限控制。
            if (subject.hasRole("admin")) {
                return "redirect:index";
            }
            else if (subject.hasRole("productManager")) {
                return "redirect:index";
            }
            else if (subject.hasRole("orderManager")) {
                return "redirect:index";
            }
            return "login";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "验证失败");
            return "login";
        }
    }
}