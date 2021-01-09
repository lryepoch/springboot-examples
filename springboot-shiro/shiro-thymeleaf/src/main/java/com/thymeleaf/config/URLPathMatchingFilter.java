package com.thymeleaf.config;

import com.thymeleaf.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * @author lryepoch
 * @date 2021/1/9 14:40
 * @description 权限校验
 */
@Slf4j
public class URLPathMatchingFilter extends PathMatchingFilter {

    @Autowired
    PermissionService permissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.info("进入PathMatchingFilter->onPreHandle……");
        if (null == permissionService) {
            permissionService = SpringContextUtils.getContext().getBean(PermissionService.class);
        }
        String requestURI = getPathWithinApplication(request);
        log.info("当前请求的URL: {}", requestURI);

        Subject subject = SecurityUtils.getSubject();
        log.info("当前用户名：{}", subject.getPrincipal());

        //检测到没有登录，就跳转到登录页面
        if (!subject.isAuthenticated()) {
            WebUtils.issueRedirect(request, response, "/login");
            return false;
        }

        //校验这个路径权限有没有维护（即查询数据库是否存在这个url），如果没有维护（不存在），一律放行(也可以改为一律不放行)
        boolean needInterceptor = permissionService.needInterceptor(requestURI);
        log.info("当前请求的URL是否需要维护: {}", needInterceptor);

        //如果设置了swagger-ui.hmtl，那么可以直接访问swagger-ui.html（数据库中没有这条url）
        if (!needInterceptor) {
            return true;
        } else {
            //查询当前用户有哪些权限url，进行比较
            boolean hasPermission = false;
            String userName = subject.getPrincipal().toString();
            Set<String> permissionUrls = permissionService.listPermissionURLs(userName);
            log.info("用户目前拥有的全部权限: {}", permissionUrls);

            for (String url : permissionUrls) {
                // 这就表示当前用户有这个权限
                if (url.equals(requestURI)) {
                    hasPermission = true;
                    break;
                }
            }

            if (hasPermission) {
                return true;
            } else {
                UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
                subject.getSession().setAttribute("ex", ex);
                WebUtils.issueRedirect(request, response, "/unauthorized");
                return false;
            }
        }
    }
}