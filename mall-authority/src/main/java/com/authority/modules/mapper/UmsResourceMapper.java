package com.authority.modules.mapper;

import com.authority.modules.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
    * 根据角色ID获取资源
    */
    List<UmsResource> getResourceListByRoleId(Long roleId);

    /**
    * 获取用户所有可访问资源
    */
    List<UmsResource> getResourceList(Long adminId);
}
