package com.anan.rbac.controller;

import com.anan.rbac.model.BaseRole;
import com.anan.rbac.model.BaseUser;
import com.anan.rbac.service.BaseMenuService;
import com.anan.rbac.service.BaseRoleService;
import com.anan.rbac.service.BaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseRoleService baseRoleService;

    @Autowired
    protected BaseMenuService baseMenuService;

    /**
     * 跳转首页
     */
    @RequestMapping("/indexPage")
    public ModelAndView index(){

        // 创建ModelAndView
        ModelAndView mav = new ModelAndView();

        // 查询用户列表数据
        BaseUser baseUser = getCurrentUser();
        List<BaseRole> roleList = baseRoleService.findRolesByUserId(baseUser.getId());

        mav.addObject("userInfo",baseUser);
        mav.addObject("roleList",roleList);

        log.info("查询到用户数据：{}",baseUser.getUserName());

        // 设置视图
        mav.setViewName("index");

        return mav;
    }

    /**
     * 获取当前用户
     * @return
     */
    protected BaseUser getCurrentUser() {
        //获取当前用户
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        //查询当前用户
        BaseUser baseUser = baseUserService.findUserByUserName(userDetails.getUsername());

        if (baseUser == null)
            throw new RuntimeException("用户不存在: " + userDetails.getUsername());

        return baseUser;
    }

}
