package com.authority.modules.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.authority.common.exception.Asserts;
import com.authority.domain.AdminUserDetails;
import com.authority.modules.dto.UmsAdminParam;
import com.authority.modules.dto.UpdateAdminPasswordParam;
import com.authority.modules.entity.*;
import com.authority.modules.mapper.UmsAdminLoginLogMapper;
import com.authority.modules.mapper.UmsAdminMapper;
import com.authority.modules.mapper.UmsResourceMapper;
import com.authority.modules.mapper.UmsRoleMapper;
import com.authority.modules.service.UmsAdminCacheService;
import com.authority.modules.service.UmsAdminRoleRelationService;
import com.authority.modules.service.UmsAdminService;
import com.authority.security.util.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired(required = false)
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Autowired
    private UmsAdminRoleRelationService umsAdminRoleRelationService;
    @Autowired(required = false)
    private UmsRoleMapper umsRoleMapper;
    @Autowired(required = false)
    private UmsResourceMapper resourceMapper;
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户，用户名唯一
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername, umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = list(wrapper);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        baseMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("账号已被禁用");
            }
            //Authentication authentication = new UsernamePasswordAuthenticationToken(用户名, 用户密码, 用户的权限集合);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            System.out.println("login()->authenticationToken：" + authenticationToken);
            //设置用户权限信息上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //登录后返回token
            token = jwtTokenUtil.generateToken(userDetails);
            //更新登录时间
            updateLoginTimeByUsername(username);
            //插入登录日志
            insertLoginLog(username);
        } catch (Exception e) {
            LOGGER.warn("登录异常：{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        //获取当前ip地址
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        umsAdminLoginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin record = new UmsAdmin();
        record.setLoginTime(new Date());
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername, username);
        update(record, wrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    private List<UmsResource> getResourceList(Long adminId) {
        //先尝试从缓存中get
        List<UmsResource> resourceList = umsAdminCacheService.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = resourceMapper.getResourceList(adminId);
        //读取完成set到缓存中
        if (CollUtil.isNotEmpty(resourceList)) {
            umsAdminCacheService.setResourceList(adminId, resourceList);
        }
        return resourceList;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //先从缓存中查询
        UmsAdmin admin = umsAdminCacheService.getAdmin(username);
        if (admin != null) {
            return admin;
        }
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername, username);
        List<UmsAdmin> adminList = list(wrapper);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            umsAdminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return umsRoleMapper.getRoleList(adminId);
    }

    @Override
    public Page<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsAdmin> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsAdmin::getUsername, keyword);
            lambda.or().like(UmsAdmin::getNickName, keyword);
        }
        return page(page, wrapper);
    }

    /**
     * MyBatis-Plus默认配置策略是：当更新数据库值时，传过来的字段为NULL时，则忽略更新。
     */
    @Override
    public boolean update(Long id, UmsAdmin admin) {
        //设置id
        admin.setId(id);
        UmsAdmin rawAdmin = getById(id);
        if (rawAdmin.getPassword().equals(passwordEncoder.encode(admin.getPassword()))) {
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        } else {
            //密码不需要修改
            if (StrUtil.isEmpty(admin.getPassword())) {
                admin.setPassword(null);
                //与原加密密码不同的需要加密修改
            } else {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        boolean success = updateById(admin);
        umsAdminCacheService.delAdmin(id);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        //删除用户信息缓存
        umsAdminCacheService.delAdmin(id);
        //删除数据库记录
        boolean success = removeById(id);
        //删除用户拥有的资源缓存
        umsAdminCacheService.delResourceList(id);
        //还要删除用户角色关系表数据
        QueryWrapper<UmsAdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdminRoleRelation::getAdminId, id);
        umsAdminRoleRelationService.remove(queryWrapper);
        return success;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam) {
        if (StrUtil.isEmpty(updateAdminPasswordParam.getUsername())
                || StrUtil.isEmpty(updateAdminPasswordParam.getOldPassword())
                || StrUtil.isEmpty(updateAdminPasswordParam.getNewPassword())) {
            return -1;
        }
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername, updateAdminPasswordParam.getUsername());
        List<UmsAdmin> adminList = list(wrapper);
        if (CollUtil.isEmpty(adminList)) {
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if (!passwordEncoder.matches(updateAdminPasswordParam.getOldPassword(), umsAdmin.getPassword())) {
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(updateAdminPasswordParam.getNewPassword()));
        updateById(umsAdmin);
        umsAdminCacheService.delAdmin(umsAdmin.getId());
        return 1;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdminRoleRelation::getAdminId, adminId);
        umsAdminRoleRelationService.remove(wrapper);
        //建立新的关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            umsAdminRoleRelationService.saveBatch(list);
        }
        umsAdminCacheService.delResourceList(adminId);
        return count;
    }
}
