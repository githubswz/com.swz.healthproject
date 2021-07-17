package com.swz.security;

import com.swz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月12日 13:20
 * @Description: TODO:
 */
public class UserService2 implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    //模拟数据库中的用户数据
    public  static Map<String, User> map = new HashMap<>();

    public void initDate(){
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("admin"));

        User user2 = new User();
        user2.setUsername("xiaoming");
        user2.setPassword(passwordEncoder.encode("1234"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }
    //认证
    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
        initDate();  //数据在存放到数据库中时都是经过加密操作的 ，该方法相当于创建一个模拟的数据库

        User admin = map.get(username);
            if (admin==null){
                return null; //用户不存在直接返回
            }
        String password = admin.getPassword(); //拿到用户的密码
        System.out.println("bcript加密后的密码： "+password);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); //注意ROLE_是默认角色固定写法
        authorities.add(new SimpleGrantedAuthority("add")); //也可以是权限
        authorities.add(new SimpleGrantedAuthority("delete")); //也可以是权限

        //此时的校验会将前台传递过来的密码已bcrypt方式加密后再与数据库的密码进行比较
        return  new org.springframework.security.core.userdetails.User(username,password,authorities);
    }
}
