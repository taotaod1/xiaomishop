package com.bt.service;

import com.bt.entity.Cart;
import com.bt.entity.Order;
import com.bt.entity.OrderDetail;
import com.bt.entity.PageBean;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 16:09
 **/
public interface OrderService {
    void submitOrder(Order order, List<Cart> cartList);

    Order findById(String outTradeNo);

    void update(Order order);

    PageBean<Order> findBypage(Integer userId, int pn, int ps);

    List<OrderDetail> findDetail(String oid);
}
