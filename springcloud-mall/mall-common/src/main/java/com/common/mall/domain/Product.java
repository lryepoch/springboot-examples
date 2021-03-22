package com.common.mall.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lryepoch
 * @date 2021/3/22 10:57
 * @description TODO 商品
 */
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getPprice() {
        return pprice;
    }

    public void setPprice(Double pprice) {
        this.pprice = pprice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
