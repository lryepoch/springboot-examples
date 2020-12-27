package com.authority.modules.controller;

import com.authority.common.domain.CommonPage;
import com.authority.common.domain.CommonResult;
import com.authority.modules.entity.UmsResource;
import com.authority.modules.service.UmsResourceService;
import com.authority.security.component.DynamicSecurityMetadataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
@Api(tags = "UmsResourceController", description = "后台资源管理")
@RestController
@RequestMapping("/modules/umsResource")
public class UmsResourceController {
    @Autowired
    private UmsResourceService umsResourceService;
    @Resource
    private DynamicSecurityMetadataSource dynamicSecurityMetadaSource;

    @ApiOperation("添加后台资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsResource umsResource) {
        boolean success = umsResourceService.create(umsResource);
        dynamicSecurityMetadaSource.clearDataSource();
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @RequestBody UmsResource umsResource) {
        boolean success = umsResourceService.update(id, umsResource);
        //删除动态权限
        dynamicSecurityMetadaSource.clearDataSource();
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<UmsResource> getItem(@PathVariable Long id) {
        UmsResource umsResource = umsResourceService.getById(id);
        return CommonResult.success(umsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {
        boolean success = umsResourceService.delete(id);
        dynamicSecurityMetadaSource.clearDataSource();
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsResource>> list(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) String nameKeyword,
                                                      @RequestParam(required = false) String urlKeyword,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsResource> resourceList = umsResourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UmsResource>> listAll() {
        List<UmsResource> resourceList = umsResourceService.list();
        return CommonResult.success(resourceList);
    }

}

