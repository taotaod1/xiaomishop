package com.bt.dao;

import com.bt.entity.Cart;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 15:27
 **/
public interface CartDao {
    Cart select(Integer uid, int pid);

    void insert(Cart cart);

    void update(Cart cart);

    List<Cart> selectByUid(Integer id);

    void delete(Integer uid, int goodsId);

    void delete(Integer uid);
}
