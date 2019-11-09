package com.anan.rbac.dao;

import com.anan.rbac.model.BaseMenu;

import java.util.List;

/**
 * 菜单dao接口
 */
public interface BaseMenuDao {

    /**
     * 查询全部菜单
     */
    List<BaseMenu> findAllMenus();
}
