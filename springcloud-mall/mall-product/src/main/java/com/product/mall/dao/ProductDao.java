package com.product.mall.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.product.mall.domain.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lryepoch
 * @date 2021/3/22 11:47
 * @description TODO
 */
@Mapper
public interface ProductDao extends BaseMapper<Product> {

}
