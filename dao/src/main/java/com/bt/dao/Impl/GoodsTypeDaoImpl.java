package com.bt.dao.Impl;

import com.bt.dao.GoodsTypeDao;
import com.bt.entity.GoodsType;
import com.bt.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 9:14
 **/
public class GoodsTypeDaoImpl implements GoodsTypeDao {
    @Override
    public List<GoodsType> selectBylevel(int i) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select * from tb_goods_type where level=?",new BeanListHandler<GoodsType>(GoodsType.class),i);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public GoodsType selectById(Integer typeid) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
           return qr.query("select * from tb_goods_type where id=?",new BeanHandler<GoodsType>(GoodsType.class),typeid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
