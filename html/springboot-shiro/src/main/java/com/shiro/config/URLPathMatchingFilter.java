package com.shiro.config;

import com.shiro.service.PermissionService;
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

@Slf4j
public class URLPathMatchingFilter extends PathMatchingFilter {
	@Autowired
	PermissionService permissionService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(null==permissionService) 
			permissionService = SpringContextUtils.getContext().getBean(PermissionService.class);
		
		String requestURI = getPathWithinApplication(request);
		log.info("拦截类里获取的requestURI:" + requestURI);

		Subject subject = SecurityUtils.getSubject();
		log.info((String) subject.getPrincipal());
		// 如果没有登录，就跳转到登录页面
		if (!subject.isAuthenticated()) {
			WebUtils.issueRedirect(request, response, "/login");
			return false;
		}

		boolean needInterceptor = permissionService.needInterceptor(requestURI);
		log.info(needInterceptor+"");
		if (!needInterceptor) {
			return true;
		} else {
			boolean hasPermission = false;
			String userName = subject.getPrincipal().toString();
			Set<String> permissionUrls = permissionService.listPermissionURLs(userName);
			for (String url : permissionUrls) {
				// 这就表示当前用户有这个权限
				if (url.equals(requestURI)) {
					hasPermission = true;
					break;
				}
			}

			if (hasPermission)
				return true;
			else {
				UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");

				subject.getSession().setAttribute("ex", ex);

				WebUtils.issueRedirect(request, response, "/unauthorized");
				return false;
			}

		}

	}
}