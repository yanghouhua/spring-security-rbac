package com.anan.rbac.security.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录过滤器
 */
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter{

    private static final String SPRING_SECURITY_RESTFUL_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_RESTFUL_PASSWORD_KEY = "password";
    private static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/login";
    private String usernameParameter = SPRING_SECURITY_RESTFUL_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_RESTFUL_PASSWORD_KEY;
    private boolean postOnly = true;

    public CustomLoginFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // 必须是post请求
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        // 获取请求参数（用户名称和密码）
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();

        // 验证码参数
        String verifyCode = request.getParameter("verifyCode");
        System.out.println("验证码参数：{}"+verifyCode);

        // 生成未认证的token
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        // 进入AuthenticationManager认证流程
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }
    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }
    public String getUsernameParameter() {
        return usernameParameter;
    }
    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }
    public String getPasswordParameter() {
        return passwordParameter;
    }
    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }
    public boolean isPostOnly() {
        return postOnly;
    }
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
