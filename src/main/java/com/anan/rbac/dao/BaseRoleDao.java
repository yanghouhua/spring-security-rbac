package com.anan.rbac.dao;

import com.anan.rbac.model.BaseRole;

import java.util.List;

/**
 * 角色dao接口
 */
public interface BaseRoleDao {

    /**
     * 根据用户id查询角色信息
     */
    List<BaseRole> findRolesByUserId(String userId);

    /**
     * 根据菜单id查询角色信息
     */
    List<BaseRole> findRolesByMenuId(String menuId);
}
