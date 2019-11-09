package com.anan.rbac.security.service;

import com.anan.rbac.model.BaseRole;
import com.anan.rbac.model.BaseUser;
import com.anan.rbac.service.BaseRoleService;
import com.anan.rbac.service.BaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证服务
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    // 注入用户service
    @Autowired
    private BaseUserService baseUserService;

    // 注入角色service
    @Autowired
    private BaseRoleService baseRoleService;

    /**
     * 实现根据用户名称查询用户逻辑
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("正在执行security自定义认证方法：{}",s);

        // 角色集合
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();

        // 根据用户名称查询用户
        BaseUser baseUser = baseUserService.findUserByUserName(s);
        if (baseUser == null) {
            log.debug("当前用户不存在， 用户名:{}", s);
            throw new UsernameNotFoundException("当前用户不存在！");
        }

        // 用户禁用
        if(baseUser.getStatus()==2) {
            log.debug("用户被禁用，无法登陆 用户名:{}", s);
            throw new UsernameNotFoundException("用户被禁用！");
        }

        // 根据用户id查询角色信息
        List<BaseRole> baseRoleList = baseRoleService.findRolesByUserId(baseUser.getId());
        if (baseRoleList != null) {
            //设置角色名称
            for (BaseRole role : baseRoleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleCode());
                authorityList.add(authority);
            }
        }

        // 返回security角色
        return new org.springframework.security.core.userdetails.
                User(baseUser.getUserName(), baseUser.getUserPassword(), true, true, true, true, authorityList);
    }
}
