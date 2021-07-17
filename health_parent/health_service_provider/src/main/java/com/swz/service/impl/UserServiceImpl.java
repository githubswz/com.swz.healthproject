package com.swz.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.swz.dao.PermissionDao;
import com.swz.dao.RoleDao;
import com.swz.dao.UserDao;
import com.swz.pojo.Permission;
import com.swz.pojo.Role;
import com.swz.pojo.User;
import com.swz.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:29
 * @Description: TODO:
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override //根据用户名查询用户，以及角色权限
    public User findUserByUsername (String username){
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        //根据用户查询角色
        Set<Role> roles = roleDao.findRoleByUserId(user.getId());
        //根据角色查询权限
        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findPermissionByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
        return user;
    }
}
