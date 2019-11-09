package com.anan.rbac.service;

import com.anan.rbac.model.BaseRole;

import java.util.List;

public interface BaseRoleService {

    /**
     * 根据用户id查询角色信息
     */
    List<BaseRole> findRolesByUserId(String userId);

    /**
     * 根据菜单id查询角色信息
     */
    List<BaseRole> findRolesByMenuId(String menuId);
}
