package com.authority.modules.service;

/**
 * @author lryepoch
 * @date 2020/12/26 15:51
 * @description TODO 后台用户缓存管理Service
 */
public interface UmsAdminCacheService {

    /**
     * @author lryepoch
     * @date 2020/12/26 16:01
     * @description 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long id);
}
