package com.shiro.service.impl;

import com.shiro.entity.Role;
import com.shiro.entity.User;
import com.shiro.entity.UserRole;
import com.shiro.entity.UserRoleExample;
import com.shiro.mapper.RoleMapper;
import com.shiro.mapper.UserRoleMapper;
import com.shiro.service.RoleService;
import com.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lryepoch
 * @date 2020/5/19 17:44
 * @description TODO
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    UserService userService;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Set<String> listRoleNames(String name) {
        Set<String> roles = new HashSet<>();
        User user = userService.findByUsername(name);
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.or().andUidEqualTo(user.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        for (UserRole userRole: userRoles){
            Role role = roleMapper.selectByPrimaryKey(userRole.getRid());
            roles.add(role.getName());
        }
        return roles;
    }
}
