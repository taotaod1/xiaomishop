package com.bt.dao.Impl;

import com.bt.dao.UserDao;
import com.bt.entity.User;
import com.bt.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:59
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public void insert(User user) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        Object[] params={user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),user.getFlag(),user.getRole(),user.getCode()};
        try {
            qr.update("insert into tb_user values(null,?,?,?,?,?,?,?)",params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public User selectByUsername(String username) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select * from tb_user where username=?",new BeanHandler<User>(User.class),username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User select(String username, String pwd) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
           return  qr.query("select * from tb_user where username=? and password=?",new BeanHandler<User>(User.class),username,pwd);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
