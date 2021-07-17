package com.swz.dao;

import com.swz.pojo.Member;

/**
 * @author : 苏文致
 * @date Date : 2021年07月11日 12:07
 * @Description: TODO:
 */
public interface MemberDao {

    Member findMenberByTel (String telphone);

    void add (Member member);

}
