package com.thymeleaf.service.impl;

import com.thymeleaf.entity.*;
import com.thymeleaf.mapper.PermissionMapper;
import com.thymeleaf.service.PermissionService;
import com.thymeleaf.service.RoleService;
import com.thymeleaf.mapper.RoleMapper;
import com.thymeleaf.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lryepoch
 * @date 2020/5/19 17:48
 * @description TODO
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> listPermissionsNames(String name) {
        List<String> result = new ArrayList<>();
        List<String> roleNames = roleService.listRoleNames(name);

        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameIn(roleNames);
        List<Role> roles = roleMapper.selectByExample(roleExample);

        for (Role role:roles){
            RolePermissionExample example = new RolePermissionExample();
            example.or().andRidEqualTo(role.getId());
            List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
            for (RolePermission permission: rps){
                Permission permission1 = permissionMapper.selectByPrimaryKey(permission.getPid());
                result.add(permission1.getName());
            }
        }
        return result;
    }

    @Override
    public boolean needInterceptor(String requestURI) {
        PermissionExample example = new PermissionExample();
        example.setOrderByClause("id desc");
        List<Permission> ps = permissionMapper.selectByExample(example);
        for(Permission p:ps){
            if (p.getUrl().equals(requestURI)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> listPermissionURLs(String userName) {
        Set<String> result = new HashSet<>();
        List<String> permissionNames = listPermissionsNames(userName);
        for (String name:permissionNames){
            PermissionExample example = new PermissionExample();
            example.or().andNameEqualTo(name);
            List<Permission> permissions = permissionMapper.selectByExample(example);
            for (Permission permission:permissions){
                result.add(permission.getUrl());
            }
        }
        return result;
    }

    @Override
    public List<Permission> findAll() {
        PermissionExample example = new PermissionExample();
        return permissionMapper.selectByExample(example);
    }
}
