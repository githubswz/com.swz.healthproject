package com.swz.dao;

import com.swz.pojo.User;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:47
 * @Description: TODO:
 */
public interface UserDao {
    User findUserByUsername (String username);
}
