package com.bt.dao;

import com.bt.entity.User;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:58
 **/
public interface UserDao {
    void insert(User user);

    User selectByUsername(String username);

    User select(String username, String pwd);
}
