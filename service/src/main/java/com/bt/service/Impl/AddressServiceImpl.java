package com.bt.service.Impl;

import com.bt.dao.AddressDao;
import com.bt.dao.Impl.AddressDaoImpl;
import com.bt.entity.Address;
import com.bt.service.AddressService;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/26 10:20
 **/
public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao = new AddressDaoImpl();
    @Override
    public List<Address> find(Integer uid) {
        return addressDao.select(uid);
    }

    @Override
    public void add(Address address) {
        addressDao.insert(address);
    }

    @Override
    public void delete(int aid) {
        addressDao.delete(aid);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public void updateLevel(int aid, Integer uid) {
        addressDao.updateLevel(aid,uid);
    }
}
