package com.authority.modules.service;

import com.authority.modules.dto.UmsMenuNode;
import com.authority.modules.entity.UmsMenu;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsMenuService extends IService<UmsMenu> {
    /**
     * @author lryepoch
     * @date 2020/12/27 18:54
     * @description 创建后台菜单
     */
    boolean create(UmsMenu umsMenu);

    /**
     * @author lryepoch
     * @date 2020/12/27 18:54
     * @description 修改后台菜单
     */
    boolean update(Long id, UmsMenu umsMenu);

    /**
     * @author lryepoch
     * @date 2020/12/27 18:54
     * @description 分页查询后台菜单
     */
    Page<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * @author lryepoch
     * @date 2020/12/27 18:54
     * @description 树形结构返回所有菜单列表
     */
    List<UmsMenuNode> treeList();

    /**
     * @author lryepoch
     * @date 2020/12/27 18:55
     * @description 修改菜单显示状态
     */
    boolean updateHidden(Long id, Integer hidden);
}
