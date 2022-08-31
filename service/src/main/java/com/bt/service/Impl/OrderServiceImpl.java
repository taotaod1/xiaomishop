package com.bt.service.Impl;

import com.bt.dao.Impl.OrderDaoImpl;
import com.bt.dao.OrderDao;
import com.bt.entity.Cart;
import com.bt.entity.Order;
import com.bt.entity.OrderDetail;
import com.bt.entity.PageBean;
import com.bt.service.CartService;
import com.bt.service.OrderService;
import com.bt.utils.DruidUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 16:10
 **/
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    @Override
    public void submitOrder(Order order, List<Cart> cartList)  {
//        提交订单
        try {
            //        开启事务
            DruidUtils.begin();
//        1 向订单表中添加数据
             orderDao.inset(order);
//        2 向订单详情表中添加数据
            List<OrderDetail> orderDetailList=new ArrayList<>();
            for (Cart cart : cartList) {
//                创建订单详情
                OrderDetail orderDetail=new OrderDetail(null,order.getId(),cart.getPid(),cart.getNum(),cart.getMoney(),null);
                orderDetailList.add(orderDetail);
//                orderDao.insetDetail(orderDetail);
            }
            orderDao.insetDetail(orderDetailList);
//        3 清空购物车信息
            CartService cartService=new CartServiceImpl();
            cartService.delete(order.getUid());
//            4 提交
            DruidUtils.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                DruidUtils.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        }finally {
            try {
                DruidUtils.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Order findById(String outTradeNo) {
        return orderDao.selectById(outTradeNo);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public PageBean<Order> findBypage(Integer userId, int pn, int ps) {

        long totalSize=orderDao.selectCount(userId);
        List<Order> orderList=orderDao.selectByPage((pn-1)*ps,ps,userId);
        PageBean<Order> pageBean=new PageBean<>(pn,ps,totalSize,orderList);
        return pageBean;
    }

    @Override
    public List<OrderDetail> findDetail(String oid) {
        return orderDao.selectDetail(oid);

    }
}
