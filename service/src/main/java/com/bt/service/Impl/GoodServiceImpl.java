package com.bt.service.Impl;

import com.bt.dao.GoodsDao;
import com.bt.dao.Impl.GoodsDaoImpl;
import com.bt.entity.Goods;
import com.bt.entity.GoodsType;
import com.bt.entity.PageBean;
import com.bt.service.GoodsService;
import com.bt.service.GoodsTypeService;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 10:23
 **/
public class GoodServiceImpl implements GoodsService {
    private GoodsDao goodsDao=new GoodsDaoImpl();
    @Override
    public PageBean<Goods> findByPage(int pn, int ps, String where, List<Object> params) {
        long totalSize=goodsDao.selectCount(where,params);
//        select * from goods where typeid=? and name =? limit ?,?
        params.add((pn-1)*ps);
        params.add(ps);
        List<Goods> data=goodsDao.selectBypage(where,params);
        System.out.println(data);
        PageBean<Goods> pageBean=new PageBean<>(pn,ps,totalSize,data);
        return pageBean;
    }

    @Override
    public Goods findById(int parseInt) {
        Goods goods = goodsDao.selectById(parseInt);
        if(goods!=null){
            GoodsTypeService goodsTypeService=new GoodsTypeServiceImpl();
            GoodsType goodsType = goodsTypeService.findById(goods.getTypeid());
            goods.setGoodsType(goodsType);
        }
        return goods;
    }

    @Override
    public void addGoods(Goods goods) {
       goodsDao.insert(goods);
    }

    @Override
    public List<Goods> findAll() {
        return goodsDao.selectAll();
    }
}
