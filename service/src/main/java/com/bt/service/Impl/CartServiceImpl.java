package com.bt.service.Impl;

import com.bt.dao.CartDao;
import com.bt.dao.Impl.CartDaoImpl;
import com.bt.entity.Cart;
import com.bt.entity.Goods;
import com.bt.service.CartService;
import com.bt.service.GoodsService;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 15:25
 **/
public class CartServiceImpl implements CartService {
    private CartDao cartDao = new CartDaoImpl();
    @Override
    public Cart findCard(Integer uid, int pid) {
        return cartDao.select(uid,pid);
    }

    @Override
    public void add(Cart cart) {
        cartDao.insert(cart);
    }

    @Override
    public void update(Cart cart) {
        cartDao.update(cart);
    }

    @Override
    public List<Cart> findByUid(Integer id) {
        List<Cart> cartList = cartDao.selectByUid(id);
        if(cartList!=null){
            GoodsService goodsService = new GoodServiceImpl();
            for (Cart cart : cartList) {
                Goods goods = goodsService.findById(cart.getPid());
                cart.setGoods(goods);
            }
        }
        return cartList;
    }

    @Override
    public void delete(Integer uid, int goodsId) {
        cartDao.delete(uid,goodsId);
    }

    @Override
    public void delete(Integer uid) {
        cartDao.delete(uid);
    }
}
