package com.bt.service;

import com.bt.entity.User;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:56
 **/
public interface UserService {

    void regist(User user);

    User findByUsername(String username);

    User login(String username, String password);
}
