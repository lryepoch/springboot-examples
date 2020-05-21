package com.shiro.service.impl;

import com.shiro.entity.*;
import com.shiro.mapper.PermissionMapper;
import com.shiro.mapper.RoleMapper;
import com.shiro.mapper.RolePermissionMapper;
import com.shiro.service.PermissionService;
import com.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Set<String> listPermissionsNames(String name) {
        Set<String> result = new HashSet<>();
        Set<String> roleNames = roleService.listRoleNames(name);
        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameEqualTo(roleNames);
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
}
