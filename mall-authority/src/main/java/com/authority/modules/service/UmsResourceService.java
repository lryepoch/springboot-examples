package com.authority.modules.service;

import com.authority.modules.entity.UmsResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsResourceService extends IService<UmsResource> {

    /**
     * @author lryepoch
     * @date 2020/12/26 14:49
     * @description 添加资源
     */
    boolean create(UmsResource umsResource);

    /**
     * @author lryepoch
     * @date 2020/12/26 15:02
     * @description 修改资源
     */
    boolean update(Long id, UmsResource umsResource);

    /**
     * @author lryepoch
     * @date 2020/12/26 15:02
     * @description 删除资源
     */
    boolean delete(Long id);

    /**
     * @author lryepoch
     * @date 2020/12/26 15:46
     * @description 分页查询资源
     */
    Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
