package com.bt.dao;

import com.bt.entity.Order;
import com.bt.entity.OrderDetail;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 16:18
 **/
public interface OrderDao {
    void inset(Order order);

    void insetDetail(OrderDetail orderDetail);

    void insetDetail(List<OrderDetail> orderDetailList);

    Order selectById(String outTradeNo);

    void update(Order order);

    long selectCount(Integer userId);

    List<Order> selectByPage(int pn, int ps, Integer userId);

    List<OrderDetail> selectDetail(String oid);
}
