package com.authority.modules.service.impl;

import cn.hutool.core.util.StrUtil;
import com.authority.modules.entity.*;
import com.authority.modules.mapper.UmsMenuMapper;
import com.authority.modules.mapper.UmsResourceMapper;
import com.authority.modules.mapper.UmsRoleMapper;
import com.authority.modules.mapper.UmsRoleMenuRelationMapper;
import com.authority.modules.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Autowired(required = false)
    private UmsMenuMapper umsMenuMapper;
    @Autowired(required = false)
    private UmsResourceMapper umsResourceMapper;
    @Autowired
    private UmsRoleMenuRelationService umsRoleMenuRelationService;
    @Autowired
    private UmsRoleResourceRelationService umsRoleResourceRelationService;
    @Autowired
    private UmsAdminRoleRelationService umsAdminRoleRelationService;
    @Autowired(required = false)
    private UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;

    @Override
    public boolean create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return save(role);
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        //删除资源相关的缓存，因为用户是根据角色而绑定的资源权限
        umsAdminCacheService.delResourceListByRoleIds(ids);
        //删除用户角色关系表数据
        QueryWrapper<UmsAdminRoleRelation> qw1 = new QueryWrapper<>();
        qw1.lambda().in(UmsAdminRoleRelation::getRoleId, ids);
        umsAdminRoleRelationService.remove(qw1);
        //删除角色菜单关系表数据
        QueryWrapper<UmsRoleMenuRelation> qw2 = new QueryWrapper<>();
        qw2.lambda().in(UmsRoleMenuRelation::getRoleId, ids);
        umsRoleMenuRelationService.remove(qw2);
        //删除角色资源关系表数据
        QueryWrapper<UmsRoleResourceRelation> qw3 = new QueryWrapper<>();
        qw3.lambda().in(UmsRoleResourceRelation::getRoleId, ids);
        umsRoleResourceRelationService.remove(qw3);
        return success;
    }

    @Override
    public Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsRole> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsRole::getName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return umsMenuMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return umsResourceMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原先关系
        QueryWrapper<UmsRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenuRelation::getRoleId, roleId);
        umsRoleMenuRelationService.remove(wrapper);
        //批量插入新关系
        List<UmsRoleMenuRelation> relations = new ArrayList<>();
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relations.add(relation);
        }
        //批量插入
        umsRoleMenuRelationService.saveBatch(relations);
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        QueryWrapper<UmsRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleResourceRelation::getRoleId, roleId);
        umsRoleResourceRelationService.remove(wrapper);
        //批量插入新关系
        List<UmsRoleResourceRelation> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        //批量插入
        umsRoleResourceRelationService.saveBatch(relationList);
        //删除资源缓存
        umsAdminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return umsMenuMapper.getMenuList(adminId);
    }

}
