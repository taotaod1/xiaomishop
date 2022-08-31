package com.bt.service.Impl;

import com.bt.dao.GoodsTypeDao;
import com.bt.dao.Impl.GoodsTypeDaoImpl;
import com.bt.entity.GoodsType;
import com.bt.service.GoodsTypeService;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 9:08
 **/
public class GoodsTypeServiceImpl implements GoodsTypeService {
    private GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
    @Override
    public List<GoodsType> findBylevel(int i) {
        return goodsTypeDao.selectBylevel(i);
    }

    @Override
    public GoodsType findById(Integer typeid) {
        return goodsTypeDao.selectById(typeid);
    }
}
