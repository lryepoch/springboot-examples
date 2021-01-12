package com.jsp.service;

import com.jsp.entity.Permission;
import com.jsp.entity.Role;

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

    List<Permission> list();

    Permission get(long id);

    void update(Permission permission);

    void add(Permission permission);

    void delete(long id);

    List<Permission> list(Role role);
}
