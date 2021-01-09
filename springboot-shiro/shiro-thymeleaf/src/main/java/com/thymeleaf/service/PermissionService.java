package com.thymeleaf.service;

import com.thymeleaf.entity.Permission;

import java.util.List;
import java.util.Set;

/**
 * @author lryepoch
 * @date 2020/5/19 17:48
 * @description TODO
 */
public interface PermissionService {
    List<String> listPermissionsNames(String name);

    boolean needInterceptor(String requestURI);

    Set<String> listPermissionURLs(String userName);

    List<Permission> findAll();
}
