package com.anan.rbac.security.filter;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * 授权过滤器
 */
public class CustomSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{

    // 资源组件
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    /**
     * 过滤器方法
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);

        invoke(fi);
    }

    // 进行下一跳处理
    public void invoke(FilterInvocation fi) throws IOException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (ServletException e) {
            super.afterInvocation(token, null);
        }
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    // 初始化
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    // 销毁
    public void destroy() {
    }

    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public boolean isLoggable(LogRecord record) {
        return false;
    }

}
