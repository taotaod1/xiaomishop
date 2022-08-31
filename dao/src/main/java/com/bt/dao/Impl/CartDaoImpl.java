package com.bt.dao.Impl;

import com.bt.dao.CartDao;
import com.bt.entity.Cart;
import com.bt.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 15:27
 **/
public class CartDaoImpl implements CartDao {
    @Override
    public Cart select(Integer uid, int pid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
           return  qr.query("select * from tb_cart where id=? and pid=?",new BeanHandler<Cart>(Cart.class),uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Cart cart) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("insert into tb_cart values (?,?,?,?)",cart.getId(),cart.getPid(),cart.getNum(),cart.getMoney());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Cart cart) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("update tb_cart set num=?,money=? where id=? and pid=?",cart.getNum(),cart.getMoney(),cart.getId(),cart.getPid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Cart> selectByUid(Integer id) {
        QueryRunner qr=new QueryRunner();
        Connection conn=DruidUtils.getConnection();
        try {
            return qr.query(conn,"select * from tb_cart where id=?",new BeanListHandler<Cart>(Cart.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer uid, int goodsId) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("delete from tb_cart where id=? and pid=?",uid,goodsId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer uid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("delete from tb_cart where id=?",uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
