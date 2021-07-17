package com.swz.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.pojo.Permission;
import com.swz.pojo.Role;
import com.swz.pojo.User;
import com.swz.service.security.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:12
 * @Description: TODO:
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
        //将用户以及用户对应的角色，角色对应的权限都查出来
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        //授权
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            //添加角色
            authorityList.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //添加权限
                authorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        //bcript加密认证
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorityList);
    }

}
