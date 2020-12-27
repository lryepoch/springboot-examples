package com.authority.modules.controller;


import com.authority.common.domain.CommonResult;
import com.authority.modules.entity.UmsResourceCategory;
import com.authority.modules.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
@Api(tags = "UmsResourceCategoryController", description = "后台资源分类管理")
@RestController
@RequestMapping("/modules/umsResourceCategory")
public class UmsResourceCategoryController {
    @Autowired
    private UmsResourceCategoryService umsResourceCategoryService;

    @ApiOperation("查询所有后台资源分类")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UmsResourceCategory>> listAll() {
        List<UmsResourceCategory> resourceCategoryList = umsResourceCategoryService.listAll();
        return CommonResult.success(resourceCategoryList);
    }

    @ApiOperation("添加后台资源分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = umsResourceCategoryService.create(umsResourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台资源分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @RequestBody UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        boolean success = umsResourceCategoryService.updateById(umsResourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id){
        boolean success = umsResourceCategoryService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

}

