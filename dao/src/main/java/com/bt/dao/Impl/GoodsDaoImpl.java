package com.bt.dao.Impl;

import com.bt.dao.GoodsDao;
import com.bt.entity.Goods;
import com.bt.entity.GoodsType;
import com.bt.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 10:26
 **/
public class GoodsDaoImpl implements GoodsDao {
    @Override
    public long selectCount(String where, List<Object> params) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return  qr.query("select count(*) from tb_goods"+where,new ScalarHandler<>(),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Goods> selectBypage(String where, List<Object> params) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select id,name,pubdate as pubDate,picture,price,star,intro,typeid from tb_goods"+where+" limit ?,?",new BeanListHandler<Goods>(Goods.class),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Goods selectById(int parseInt) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return  qr.query("select id,name,pubdate as pubDate,picture,price,star,intro,typeid from tb_goods where id=?",new BeanHandler<Goods>(Goods.class),parseInt);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Goods goods) {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            qr.update("insert into tb_goods values (null,?,?,?,?,?,?,?)",goods.getName(),goods.getPubDate(),goods.getPicture(),goods.getPrice(),goods.getStar(),goods.getIntro(),goods.getTypeid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Goods> selectAll() {
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        try {
            return qr.query("select g.*,gt.name from tb_goods as g inner join tb_goods_type as gt where g.typeid=gt.id", new ResultSetHandler<List<Goods>>() {
                @Override
                public List<Goods> handle(ResultSet rs) throws SQLException {
                    List<Goods> goodsList=new ArrayList<>();
                    if(rs!=null){
                        while (rs.next()){
                            Goods goods=new Goods();
                            goods.setId(rs.getInt("g.id"));
                            goods.setName(rs.getString("g.name"));
                            goods.setPubDate(rs.getTimestamp("g.pubdate"));
                            goods.setPicture(rs.getString("g.picture"));
                            goods.setPrice(rs.getBigDecimal("g.price"));
                            goods.setStar(rs.getInt("g.star"));
                            goods.setIntro(rs.getString("g.intro"));
                            GoodsType goodsType=new GoodsType();
                            goodsType.setName(rs.getString("gt.name"));
                            goods.setGoodsType(goodsType);
                            goodsList.add(goods);
                        }
                    }
                    return goodsList;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
