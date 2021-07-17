package com.swz.service;

import com.swz.pojo.Member;

/**
 * @author : 苏文致
 * @date Date : 2021年07月12日 10:08
 * @Description: TODO:
 */
public interface MemberService {
    Member findByTelephone (String telephone);

    void add (Member member);
}
