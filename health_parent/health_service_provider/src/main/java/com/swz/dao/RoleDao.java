package com.swz.dao;

import com.swz.pojo.Role;

import java.util.Set;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:51
 * @Description: TODO:
 */
public interface RoleDao {
    Set<Role> findRoleByUserId (Integer id);
}
