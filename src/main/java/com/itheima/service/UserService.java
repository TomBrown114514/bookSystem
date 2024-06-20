/**
 * 用户服务接口，定义了用户登录的操作。
 * 本接口旨在提供用户身份验证的功能，通过接收用户信息来判断用户登录是否成功。
 */
package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
    /**
     * 用户登录方法。
     *
     * @param user 用户对象，包含登录所需的用户名和密码等信息。
     * @return 如果登录成功，返回用户对象；如果登录失败，返回null。
     * 该方法通过比较数据库中的用户信息来验证登录凭证的有效性。
     */
    User login(User user);
}
