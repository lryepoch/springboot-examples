package com.authority.modules.mapper;

import com.authority.modules.entity.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    /**
     * @author lryepoch
     * @date 2020/12/26 16:17
     * @description 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(Long resourceId);
}
