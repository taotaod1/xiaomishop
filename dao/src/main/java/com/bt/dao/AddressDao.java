package com.bt.dao;

import com.bt.entity.Address;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 10:23
 **/
public interface AddressDao {
    List<Address> select(Integer uid);

    void insert(Address address);

    void delete(int aid);

    void update(Address address);

    void updateLevel(int aid, Integer uid);
}
