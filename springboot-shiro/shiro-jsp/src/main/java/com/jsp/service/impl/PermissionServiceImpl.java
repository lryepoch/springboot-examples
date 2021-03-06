package com.jsp.service.impl;

import com.jsp.entity.*;
import com.jsp.mapper.PermissionMapper;
import com.jsp.mapper.RoleMapper;
import com.jsp.mapper.RolePermissionMapper;
import com.jsp.service.PermissionService;
import com.jsp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> listPermissionsNames(String name) {
        List<String> result = new ArrayList<>();
        List<String> roleNames = roleService.listRoleNames(name);

        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameIn(roleNames);
        List<Role> roles = roleMapper.selectByExample(roleExample);

        for (Role role : roles) {
            RolePermissionExample example = new RolePermissionExample();
            example.or().andRidEqualTo(role.getId());
            List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
            for (RolePermission permission : rps) {
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
        for (Permission p : ps) {
            if (p.getUrl().equals(requestURI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> listPermissionURLs(String userName) {
        Set<String> result = new HashSet<>();
        List<String> permissionNames = listPermissionsNames(userName);
        for (String name : permissionNames) {
            PermissionExample example = new PermissionExample();
            example.or().andNameEqualTo(name);
            List<Permission> permissions = permissionMapper.selectByExample(example);
            for (Permission permission : permissions) {
                result.add(permission.getUrl());
            }
        }
        return result;
    }

    @Override
    public List<Permission> list() {
        PermissionExample example = new PermissionExample();
        example.setOrderByClause("id desc");
        return permissionMapper.selectByExample(example);
    }

    @Override
    public Permission get(long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void add(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void delete(long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Permission> list(Role role) {
        List<Permission> result = new ArrayList<>();
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRidEqualTo(role.getId());
        List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
        for (RolePermission rolePermission : rps) {
            result.add(permissionMapper.selectByPrimaryKey(rolePermission.getPid()));
        }
        return result;
    }
}
