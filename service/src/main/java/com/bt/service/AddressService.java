package com.bt.service;

import com.bt.entity.Address;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 10:20
 **/
public interface AddressService {
    List<Address> find(Integer uid);

    void add(Address address);

    void delete(int aid);

    void update(Address address);

    void updateLevel(int aid, Integer uid);
}
