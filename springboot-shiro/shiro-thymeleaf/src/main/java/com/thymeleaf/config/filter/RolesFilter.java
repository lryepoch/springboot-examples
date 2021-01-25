package com.thymeleaf.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lryepoch
 * @date 2020/12/31 10:54
 * @description TODO AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器
 */
@Slf4j
public class RolesFilter extends AuthorizationFilter {

    /**
     * isAccessAllowed()是否允许访问，mappedValue的值就是[urls]配置的参数，允许访问返回 true，否则返回flase
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.info("进入角色查询拦截器……");

//        boolean allowed = super.isAccessAllowed(request, response, mappedValue);
//        if (!allowed) {
//            String method = WebUtils.toHttp(request).getMethod();
//            if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
//                return true;
//            }
//        }

        Subject subject = getSubject(request, response);
        log.info("subject：{}", subject);
        String[] rolesArray = (String[]) mappedValue;
        for (String role : rolesArray) {
            log.info("rolesArray：{}", role);
        }

        //没有角色限制，有访问权限
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        for (int i = 0; i < rolesArray.length; i++) {
            log.info("当前用户是否拥有当前访问权限的角色：{}", subject.hasRole(rolesArray[i]));
            //若当前用户是rolesArray中的任何一个，则有权限访问
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }
        return false;
    }
}
