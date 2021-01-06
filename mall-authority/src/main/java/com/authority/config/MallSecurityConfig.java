package com.authority.config;

import com.authority.modules.entity.UmsResource;
import com.authority.modules.service.UmsAdminService;
import com.authority.modules.service.UmsResourceService;
import com.authority.security.component.DynamicSecurityService;
import com.authority.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lryepoch
 * @date 2020/12/27 17:37
 * @description mall-security模块相关配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    /**
     * @author lryepoch
     * @date 2021/1/4 11:00
     * @description UserDetailsService接口只提供一个方法loadUserByUsername()，
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        UserDetailsService userDetailsService = username -> adminService.loadUserByUsername(username);
        return userDetailsService;
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.list();
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
