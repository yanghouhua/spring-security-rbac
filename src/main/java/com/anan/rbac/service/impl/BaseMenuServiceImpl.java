package com.anan.rbac.service.impl;

import com.anan.rbac.dao.BaseMenuDao;
import com.anan.rbac.model.BaseMenu;
import com.anan.rbac.service.BaseMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单service实现类
 */
@Service
public class BaseMenuServiceImpl implements BaseMenuService{

    // 注入菜单dao
    @Autowired
    private BaseMenuDao baseMenuDao;

    /**
     * 根据请求url查询菜单列表数据
     *
     * @param url
     */
    @Override
    public List<BaseMenu> findMenuListByUrl(String url) {
        List<BaseMenu> menuList = baseMenuDao.findAllMenus();
        List<BaseMenu> menus = new ArrayList<>();
        if (menuList != null) {
            menus.addAll(menuList.stream().filter(menu -> urlMatcher(menu.getMenuUrl(), url)).collect(Collectors.toList()));
        }
        return menus;
    }

    //=======================================================
    private boolean urlMatcher(String permStr, String url) {
        if (permStr == null) {
            return false;
        }
        PathMatcher matcher = new AntPathMatcher();
        return matcher.match(permStr, url);
    }
}
