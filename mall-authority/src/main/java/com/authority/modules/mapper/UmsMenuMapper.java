package com.authority.modules.mapper;

import com.authority.modules.entity.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
    * 根据角色ID获取菜单
    */
    List<UmsMenu> getMenuListByRoleId(Long roleId);

    /**
    * 根据后台用户ID获取菜单
    */
    List<UmsMenu> getMenuList(Long adminId);
}
