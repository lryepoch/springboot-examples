package com.order.mall.service.impl;

import com.common.mall.domain.Order;
import com.order.mall.dao.OrderDao;
import com.order.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lryepoch
 * @date 2021/3/22 12:24
 * @description TODO
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    public void createOrder(Order order) {
        orderDao.insert(order);
    }
}
