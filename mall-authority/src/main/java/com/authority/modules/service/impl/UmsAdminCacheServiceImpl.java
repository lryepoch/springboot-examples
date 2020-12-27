package com.authority.modules.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.authority.common.service.RedisService;
import com.authority.modules.entity.UmsAdminRoleRelation;
import com.authority.modules.mapper.UmsAdminMapper;
import com.authority.modules.service.UmsAdminCacheService;
import com.authority.modules.service.UmsAdminRoleRelationService;
import com.authority.modules.service.UmsAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/12/26 15:52
 * @description TODO 后台用户缓存管理Service实现类
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private UmsAdminRoleRelationService umsAdminRoleRelationService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = umsAdminMapper.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> ids) {
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(UmsAdminRoleRelation::getRoleId, ids);
        List<UmsAdminRoleRelation> relationList = umsAdminRoleRelationService.list(wrapper);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

}
