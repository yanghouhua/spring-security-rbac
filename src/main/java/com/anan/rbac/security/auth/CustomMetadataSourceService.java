package com.anan.rbac.security.auth;

import com.anan.rbac.model.BaseMenu;
import com.anan.rbac.model.BaseRole;
import com.anan.rbac.service.BaseMenuService;
import com.anan.rbac.service.BaseRoleService;
import com.anan.rbac.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 获取请求url的权限
 */
@Component
public class CustomMetadataSourceService implements FilterInvocationSecurityMetadataSource{

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private BaseRoleService baseRoleService;

    @Autowired
    private PropertySource propertySourceBean;

    private PathMatcher matcher = new AntPathMatcher();

    private String indexUrl = "/index.html";

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取当前访问url
        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }

        // 角色权限集合
        List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();

        try {
            //设置不拦截
            if (propertySourceBean.getProperty("security.ignoring") != null) {
                String[] paths = propertySourceBean.getProperty("security.ignoring").toString().split(",");
                //判断是否符合规则
                for (String path: paths) {
                    String temp = StringUtil.clearSpace(path);
                    if (matcher.match(temp, url)) {
                        // 放行的url，允许匿名访问
                        ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                        result.add(attribute);
                        return result;
                    }
                }
            }

            //如果不是拦截列表里的
            /*if (!isIntercept(url)) {
                ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                result.add(attribute);
                return result;
            }*/

            //查询匹配的url
            List<BaseMenu> menuList = baseMenuService.findMenuListByUrl(url);
            if (menuList != null && menuList.size() > 0) {
                for (BaseMenu menu : menuList) {
                    //查询拥有该菜单权限的角色列表
                    List<BaseRole> roles = baseRoleService.findRolesByMenuId(menu.getId());
                    if (roles != null && roles.size() > 0) {
                        for (BaseRole role : roles) {
                            ConfigAttribute conf = new SecurityConfig(role.getRoleCode());
                            result.add(conf);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 判断是否需要过滤
     * @param url
     * @return
     */
    public boolean isIntercept(String url) {
        String[] filterPaths = propertySourceBean.getProperty("security.intercept").toString().split(",");
        for (String filter: filterPaths) {
            if (matcher.match(StringUtil.clearSpace(filter), url) & !matcher.match(indexUrl, url)) {
                return true;
            }
        }

        return false;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

}
