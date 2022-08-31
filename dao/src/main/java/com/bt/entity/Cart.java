package com.bt.entity;

import java.math.BigDecimal;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 10:18
 **/
public class Cart {
    private Integer id;
    private  Integer pid;
    private  Integer num;
    private BigDecimal money;
    private Goods goods;

    public Cart(Integer id, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }
    public Cart(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", pid=" + pid +
                ", num=" + num +
                ", money=" + money +
                '}';
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
