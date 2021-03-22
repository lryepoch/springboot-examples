package com.common.mall.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lryepoch
 * @date 2021/3/22 10:57
 * @description TODO 用户
 */
@Data
@TableName("mall_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer uid;//主键
    private String username;//用户名
    private String password;//密码
    private String telephone;//手机号
}
