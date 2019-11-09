package com.anan.rbac.dao;

import com.anan.rbac.model.BaseUser;

import java.util.List;

/**
 * 用户dao接口
 */
public interface BaseUserDao{

    /**
     * 查询全部用户列表数据
     */
    List<BaseUser> findAllUsers();

    /**
     * 根据用户名称查询用户
     */
    BaseUser findUserByUserName(String userName);

}
