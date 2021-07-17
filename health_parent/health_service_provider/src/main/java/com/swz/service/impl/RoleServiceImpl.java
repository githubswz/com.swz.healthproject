package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.swz.service.security.RoleService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 9:51
 * @Description: TODO:
 */
@Service(interfaceClass =RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
}
