package com.anan.rbac.security.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理匿名用户访问无权限资源时的异常
 */
public class CustomLoginAuthEntryPoint implements AuthenticationEntryPoint{

    // 登录url
    private String loginUrl = "/login.html";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if(isAjaxRequest(request)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
        }else{
            response.sendRedirect(loginUrl);
        }
    }

    /**
     * 判断是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

    //=================================================
    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
