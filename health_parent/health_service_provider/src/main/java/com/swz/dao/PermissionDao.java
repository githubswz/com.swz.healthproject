package com.swz.dao;

import com.swz.pojo.Permission;

import java.util.Set;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:53
 * @Description: TODO:
 */
public interface PermissionDao {
    Set<Permission> findPermissionByRoleId (Integer id);
}
