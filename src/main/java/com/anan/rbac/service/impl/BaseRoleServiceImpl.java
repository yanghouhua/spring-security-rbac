package com.anan.rbac.service.impl;

import com.anan.rbac.dao.BaseRoleDao;
import com.anan.rbac.model.BaseRole;
import com.anan.rbac.service.BaseRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色services实现类
 */
@Service
public class BaseRoleServiceImpl implements BaseRoleService{

    // 注入角色dao
    @Autowired
    private BaseRoleDao baseRoleDao;

    /**
     * 根据用户id查询角色信息
     *
     * @param userId
     */
    @Override
    public List<BaseRole> findRolesByUserId(String userId) {
        return baseRoleDao.findRolesByUserId(userId);
    }

    /**
     * 根据菜单id查询角色信息
     *
     * @param menuId
     */
    @Override
    public List<BaseRole> findRolesByMenuId(String menuId) {
        return baseRoleDao.findRolesByMenuId(menuId);
    }
}
