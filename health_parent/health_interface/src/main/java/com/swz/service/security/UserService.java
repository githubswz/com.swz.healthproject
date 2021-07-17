package com.swz.service.security;

import com.swz.pojo.User;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:16
 * @Description: TODO:
 */
public interface UserService {
    //根据用户名查询用户
    User findUserByUsername (String username);
}
