package com.authority.modules.service;

import com.authority.modules.entity.UmsResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源分类表 服务类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * @author lryepoch
     * @date 2020/12/26 11:09
     * @description 获取所有资源分类【商品、订单、权限、营销、内容】
     */
    List<UmsResourceCategory> listAll();

    /**
     * @author lryepoch
     * @date 2020/12/26 11:13
     * @description 创建资源分类
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
