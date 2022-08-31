package com.bt.dao.Impl;

import com.bt.dao.OrderDao;
import com.bt.entity.Address;
import com.bt.entity.Goods;
import com.bt.entity.Order;
import com.bt.entity.OrderDetail;
import com.bt.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 16:18
 **/
public class OrderDaoImpl implements OrderDao {
    @Override
    public void inset(Order order) {
        QueryRunner qr=new QueryRunner();
        Connection conn= DruidUtils.getConnection();
        try {
            qr.update(conn,"insert into tb_order values (?,?,?,?,?,?)",order.getId(),order.getUid(),order.getMoney(),order.getStatus(),order.getTime(),order.getAid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insetDetail(OrderDetail orderDetail) {
        QueryRunner qr=new QueryRunner();
        Connection conn= DruidUtils.getConnection();
        try {
            qr.update(conn,"insert into tb_orderdetail values (null,?,?,?,?)",orderDetail.getOid(),orderDetail.getPid(),orderDetail.getNum(),orderDetail.getMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insetDetail(List<OrderDetail> orderDetailList) {
        QueryRunner qr=new QueryRunner();
        Connection conn= DruidUtils.getConnection();
        Object[][] params=new Object[orderDetailList.size()][];
        if(orderDetailList!=null){
            for (int i = 0; i < orderDetailList.size(); i++) {
                params[i]=new Object[]{orderDetailList.get(i).getOid(),orderDetailList.get(i).getPid(),orderDetailList.get(i).getNum(),orderDetailList.get(i).getMoney()};
            }
        }
        try {
            qr.batch(conn,"insert into tb_orderdetail values (null,?,?,?,?)",params);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Order selectById(String outTradeNo) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select o.*,a.detail,a.name,a.phone from tb_order as o inner join tb_address as a on o.aid=a.id where o.id=?", new ResultSetHandler<Order>() {
                @Override
                public Order handle(ResultSet rs) throws SQLException {
                    Order order=new Order();
                    if(rs!=null){
                        while (rs.next()){
                            order.setId(rs.getString("id"));
                            order.setUid(rs.getInt("uid"));
                            order.setMoney(rs.getBigDecimal("money"));
                            order.setStatus(rs.getString("status"));
                            order.setTime(rs.getTimestamp("time"));
                            order.setAid(rs.getInt("aid"));
                            Address address=new Address();
                            address.setName(rs.getString("a.name"));
                            address.setDetail(rs.getString("a.detail"));
                            address.setPhone(rs.getString("phone"));
                            order.setAddress(address);
                        }
                        return order;
                    }
                    return null;
                }
            }, outTradeNo);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Order order) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("update tb_order set status=? where id=?",order.getStatus(),order.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public long selectCount(Integer userId) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select count(*) from tb_order where uid=?",new ScalarHandler<>(),userId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> selectByPage(int pn, int ps, Integer userId) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select o.*,a.id,a.name,a.detail from tb_order as o inner join tb_address as a on o.aid=a.id where o.uid=? limit ?,?", new ResultSetHandler<List<Order>>() {
                @Override
                public List<Order> handle(ResultSet rs) throws SQLException {
                    List<Order> list=new ArrayList<>();
                    while (rs.next()){
                        Order order=new Order();
                        order.setId(rs.getString("o.id"));
                        order.setUid(rs.getInt("o.uid"));
                        order.setMoney(rs.getBigDecimal("o.money"));
                        order.setStatus((String) rs.getObject("o.status"));
                        order.setTime(rs.getTimestamp("o.time"));
                        Address address=new Address();
                        address.setId(rs.getInt("o.aid"));
                        address.setDetail(rs.getString("a.detail"));
                        address.setName(rs.getString("a.name"));
                        order.setAddress(address);
                        list.add(order);
                    }
                    return list;
                }
            }, userId,pn,ps);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> selectDetail(String oid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select od.oid,od.num,od.money,g.* from tb_orderdetail as od inner join tb_goods as g on od.pid=g.id where od.oid=?", new ResultSetHandler<List<OrderDetail>>() {
                @Override
                public List<OrderDetail> handle(ResultSet rs) throws SQLException {
                   List<OrderDetail> orderDetailList=new ArrayList<>();
                   if (rs!=null){
                       while (rs.next()){
                           OrderDetail orderDetail=new OrderDetail();
                           orderDetail.setOid(rs.getString("od.oid"));
                           orderDetail.setNum(rs.getInt("od.num"));
                           orderDetail.setMoney(rs.getBigDecimal("od.money"));
                           Goods goods=new Goods();
                           goods.setId(rs.getInt("g.id"));
                           goods.setName(rs.getString("g.name"));
                           goods.setPubDate(rs.getTimestamp("g.pubdate"));
                           goods.setPicture(rs.getString("g.picture"));
                           goods.setStar(rs.getInt("g.star"));
                           goods.setIntro(rs.getString("g.intro"));
                           goods.setTypeid(rs.getInt("g.typeid"));
                           orderDetail.setGoods(goods);
                           orderDetailList.add(orderDetail);
                       }
                       return orderDetailList;
                   }
                   return null;
                }
            }, oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
