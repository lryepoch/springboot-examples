package com.authority.modules.service;

import com.authority.modules.entity.UmsAdmin;
import com.authority.modules.entity.UmsResource;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/12/26 15:51
 * @description TODO 后台用户缓存管理Service
 */
public interface UmsAdminCacheService {

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long id);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> ids);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
    * 获取缓存后台用户资源列表
    */
    List<UmsResource> getResourceList(Long adminId);

    /**
    * 设置后台用户资源列表
    */
    void setResourceList(Long adminId, List<UmsResource> resourceList);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long adminId);

    /**
    * 获取缓存后台用户信息
    */
    UmsAdmin getAdmin(String username);

    /**
    * 设置缓存后台用户信息
    */
    void setAdmin(UmsAdmin admin);

    /**
    * 删除后台用户缓存
    */
    void delAdmin(Long id);


}
