package com.authority.modules.mapper;

import com.authority.modules.entity.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
    * 获取用户所有角色
    */
    List<UmsRole> getRoleList(Long adminId);
}
