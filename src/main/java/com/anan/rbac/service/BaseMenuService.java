package com.anan.rbac.service;

import com.anan.rbac.model.BaseMenu;

import java.util.List;

public interface BaseMenuService {

    /**
     * 根据请求url查询菜单列表数据
     */
    List<BaseMenu> findMenuListByUrl(String url);
}
