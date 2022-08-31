package com.bt.dao.Impl;

import com.bt.dao.AddressDao;
import com.bt.entity.Address;
import com.bt.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 10:23
 **/
public class AddressDaoImpl implements AddressDao {
    @Override
    public List<Address> select(Integer uid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select * from tb_address where uid=?",new BeanListHandler<Address>(Address.class),uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Address address) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
           qr.update("insert into tb_address values(null,?,?,?,?,?)",address.getDetail(),address.getName(),address.getPhone(),address.getUid(),address.getLevel());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int aid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("delete from tb_address where id=?",aid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Address address) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("update tb_address set detail=?,name=?,phone=?,level=? where id=?",address.getDetail(),address.getName(),address.getPhone(),address.getLevel(),address.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLevel(int aid, Integer uid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("update tb_address set level=0 where uid=?",uid);
            qr.update("update tb_address set level=1 where id=? and uid=?",aid,uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
