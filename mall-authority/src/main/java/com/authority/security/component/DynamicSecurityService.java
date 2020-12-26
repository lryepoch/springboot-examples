package com.authority.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/12/26 14:24
 * @description TODO 动态权限相关业务类
 */
public interface DynamicSecurityService {

    /**
     * @author lryepoch
     * @date 2020/12/26 14:25
     * @description 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
