package com.authority.modules.dto;

import com.authority.modules.entity.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 18:51
 * @Description 后台菜单节点封装
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNode> children;
}
