package com.thymeleaf.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lryepoch
 * @date 2020/12/31 10:54
 * @description TODO AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器
 */
@Slf4j
public class RolesFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("进入角色查询拦截器……");
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        log.info("rolesArray：{}", rolesArray);

        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) { //若当前用户是rolesArray中的任何一个，则有权限访问
                return true;
            }
        }
        return false;
    }
}
