package com.authority.modules.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.authority.modules.mapper.UmsAdminMapper;
import com.authority.modules.service.RedisService;
import com.authority.modules.service.UmsAdminCacheService;
import com.authority.modules.service.UmsAdminRoleRelationService;
import com.authority.modules.service.UmsAdminService;
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
    private UmsAdminMapper adminMapper;
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
        List<Long> adminIdList = adminMapper.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

}
