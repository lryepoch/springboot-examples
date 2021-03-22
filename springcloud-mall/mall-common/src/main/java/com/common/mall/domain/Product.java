package com.common.mall.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lryepoch
 * @date 2021/3/22 10:57
 * @description TODO 商品
 */
@Data
@TableName("mall_product")
public class Product {
    //主键
    @TableId(type = IdType.AUTO)
    private Integer pid;
    //商品名称
    private String pname;
    //商品价格
    private Double pprice;
    //库存
    private Integer stock;
}
