package com.bt.dao;

import com.bt.entity.GoodsType;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 9:14
 **/
public interface GoodsTypeDao {
    List<GoodsType> selectBylevel(int i);

    GoodsType selectById(Integer typeid);
}
