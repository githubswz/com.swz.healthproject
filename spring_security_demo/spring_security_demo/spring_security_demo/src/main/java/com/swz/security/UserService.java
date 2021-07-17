package com.swz.security;

import com.swz.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月12日 13:20
 * @Description: TODO:
 */
public class UserService implements UserDetailsService {
    //模拟数据库中的用户数据
    public  static Map<String, User> map = new HashMap<>();
    static {
        com.swz.pojo.User user1 = new com.swz.pojo.User();
        user1.setUsername("admin");
        user1.setPassword("admin");

        com.swz.pojo.User user2 = new com.swz.pojo.User();
        user2.setUsername("xiaoming");
        user2.setPassword("1234");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }
    //认证
    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
        System.out.println("username:" + username);
        /**********用户名查询是否存在************/
        //根据用户名去数据库查询用户信息
        User admin = map.get(username);
            if (admin==null){
                return null; //用户不存在直接返回
            }
        String password = admin.getPassword(); //拿到用户的密码

       /*************数据库查询用户权限并授权****************/
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); //注意ROLE_是默认角色固定写法
        authorities.add(new SimpleGrantedAuthority("add")); //也可以是权限
        authorities.add(new SimpleGrantedAuthority("delete")); //也可以是权限

        //将权限加入到内存中，此外配置文件已经将用户输入的用户名和密码保存，此时传入的用户名和密码就可以进行校验了
        /*******************springSecurity进行认证************************/
        //{noop}password 表示密码为明文（校验时不需要将前台的密码进行加密校验）
        return  new org.springframework.security.core.userdetails.User(username,"{noop}"+password,authorities);
    }
}
