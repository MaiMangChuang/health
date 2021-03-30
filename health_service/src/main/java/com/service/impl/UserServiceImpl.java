package com.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dao.UserDao;
import com.pojo.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description: No Description
 * User: Eric
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过用户名查询用户信息 包含角色与权限
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
