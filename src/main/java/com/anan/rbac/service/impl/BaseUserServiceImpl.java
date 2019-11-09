package com.anan.rbac.service.impl;

import com.anan.rbac.dao.BaseUserDao;
import com.anan.rbac.model.BaseUser;
import com.anan.rbac.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户service实现类
 */
@Service
public class BaseUserServiceImpl implements BaseUserService {

    // 注入用户dao
    @Autowired
    private BaseUserDao baseUserDao;

    @Override
    public List<BaseUser> findAllUsers() {
        return baseUserDao.findAllUsers();
    }

    /**
     * 根据用户名称查询用户
     *
     * @param userName
     */
    public BaseUser findUserByUserName(String userName) {
        return baseUserDao.findUserByUserName(userName);
    }
}
