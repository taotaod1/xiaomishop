package com.bt.service.Impl;

import com.bt.dao.Impl.UserDaoImpl;
import com.bt.dao.UserDao;
import com.bt.entity.User;
import com.bt.service.UserService;
import com.bt.utils.EmailUtils;
import com.bt.utils.Md5Utils;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:56
 **/
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void regist(User user) {
        user.setPassword(Md5Utils.md5(user.getPassword()));
//        判断用户名是否存在
        User user1=userDao.selectByUsername(user.getUsername());
        if(user1!=null){
            throw new RuntimeException("用户名已存在");
        }
        EmailUtils.sendEmail(user);
        userDao.insert(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    @Override
    public User login(String username, String password) {
       User user= userDao.select(username, Md5Utils.md5(password));
       if(user==null){
           throw new RuntimeException("用户名或密码错误");
       }
       if (user.getFlag()!=1){
           throw new RuntimeException("该用户还未激活，请先激活");
       }
       return user;
    }
}
