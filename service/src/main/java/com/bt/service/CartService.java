package com.bt.service;

import com.bt.entity.Cart;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 15:23
 **/
public interface CartService {
//    查找订单项
    Cart findCard(Integer id, int parseInt);
//添加订单项
    void add(Cart cart);
//更新订单项目
    void update(Cart cart);
//  查找订单
    List<Cart> findByUid(Integer uid);
//  删除订单项
    void delete(Integer uid, int goodsId);
//删除订单
    void delete(Integer uid);
}
