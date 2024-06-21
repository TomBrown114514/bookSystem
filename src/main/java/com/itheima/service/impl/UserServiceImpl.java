package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务的实现类，提供关于用户操作的服务。
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 自动注入UserMapper，用于执行与用户相关的数据库操作。
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 实现登录功能。
     * 根据传入的用户对象，验证用户名和密码是否匹配。
     *
     * @param user 用户对象，包含待验证的用户名和密码。
     * @return 如果用户名和密码匹配，则返回对应的用户对象；否则返回null。
     */
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
}

