package com.bt.dao;

import com.bt.entity.Goods;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 10:26
 **/
public interface GoodsDao {
    long selectCount(String where, List<Object> params);

    List<Goods> selectBypage(String where, List<Object> params);

    Goods selectById(int parseInt);

    void insert(Goods goods);

    List<Goods> selectAll();
}
