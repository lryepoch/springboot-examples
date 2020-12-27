package com.authority.modules.service;

import com.authority.modules.entity.UmsRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * @author lryepoch
     * @date 2020/12/27 23:34
     * @description 添加角色
     */
    boolean create(UmsRole role);

    /**
     * @author lryepoch
     * @date 2020/12/27 23:34
     * @description 批量删除角色
     */
    boolean delete(List<Long> ids);

    /**
     * @author lryepoch
     * @date 2020/12/27 23:34
     * @description 分页获取角色列表
     */
    Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);
}
