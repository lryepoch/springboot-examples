package com.authority.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 后台用户登录日志表
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_admin_login_log")
@ApiModel(value="UmsAdminLoginLog对象", description="后台用户登录日志表")
public class UmsAdminLoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long adminId;

    @ApiModelProperty(value = "登录时间")
    private Date createTime;

    @ApiModelProperty(value = "登录ip")
    private String ip;

    @ApiModelProperty(value = "登录地址")
    private String address;

    @ApiModelProperty(value = "浏览器登录类型")
    private String userAgent;


}
