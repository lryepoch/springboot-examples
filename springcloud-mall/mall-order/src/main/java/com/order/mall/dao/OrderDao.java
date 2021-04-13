package com.order.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.order.mall.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lryepoch
 * @date 2021/3/22 12:23
 * @description TODO
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {
}
