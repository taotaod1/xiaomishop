package com.bt.service;

import com.bt.entity.GoodsType;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 9:08
 **/
public interface GoodsTypeService {
    List<GoodsType> findBylevel(int i);

    GoodsType findById(Integer typeid);
}
