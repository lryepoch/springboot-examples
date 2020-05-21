package com.shiro.service;

import java.util.Set;

/**
 * @author lryepoch
 * @date 2020/5/19 17:48
 * @description TODO
 */
public interface PermissionService {
    Set<String> listPermissionsNames(String name);
}
