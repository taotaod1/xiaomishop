package com.bt.service;

import com.bt.entity.Goods;
import com.bt.entity.PageBean;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 10:23
 **/
public interface GoodsService {

    PageBean<Goods> findByPage(int pn, int ps, String toString, List<Object> params);

    Goods findById(int parseInt);

    void addGoods(Goods goods);

    List<Goods> findAll();
}
