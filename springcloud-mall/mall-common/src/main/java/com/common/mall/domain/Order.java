package com.common.mall.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lryepoch
 * @date 2021/3/22 10:57
 * @description TODO 订单
 */
@TableName("mall_order")
@Data
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer oid;//订单id

    //用户
    private Integer uid;
    private String username;

    //商品
    private Integer pid;
    private String pname;
    private Double pprice;

    //购买数量
    private Integer number;

}
