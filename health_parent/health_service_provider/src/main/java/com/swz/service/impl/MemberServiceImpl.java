package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.swz.dao.MemberDao;
import com.swz.pojo.Member;
import com.swz.service.MemberService;
import com.swz.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 苏文致
 * @date Date : 2021年07月12日 10:39
 * @Description: TODO:
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;


    //根据电话查询用户信息
    @Override
    public Member findByTelephone (String telephone){
        Member member = memberDao.findMenberByTel(telephone);
        return member;
    }
    //添加会员
    @Override
    public void add (Member member){
        //对会员的密码进行md5加密
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }
}
