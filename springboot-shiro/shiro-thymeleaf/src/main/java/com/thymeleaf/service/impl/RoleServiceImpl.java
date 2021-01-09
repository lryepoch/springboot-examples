package com.thymeleaf.service.impl;

import com.thymeleaf.service.RoleService;
import com.thymeleaf.service.UserService;
import com.thymeleaf.entity.Role;
import com.thymeleaf.entity.User;
import com.thymeleaf.entity.UserRole;
import com.thymeleaf.entity.UserRoleExample;
import com.thymeleaf.mapper.RoleMapper;
import com.thymeleaf.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> listRoleNames(String name) {
        List<String> roles = new ArrayList<>();
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
