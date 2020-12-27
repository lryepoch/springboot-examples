package com.authority.modules.service.impl;

import com.authority.modules.entity.UmsRole;
import com.authority.modules.mapper.UmsRoleMapper;
import com.authority.modules.service.UmsAdminCacheService;
import com.authority.modules.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        umsAdminCacheService.delResourceListByRoleIds(ids);
        return success;
    }

    @Override
    public Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }
}
