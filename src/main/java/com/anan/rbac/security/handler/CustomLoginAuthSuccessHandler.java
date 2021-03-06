package com.anan.rbac.security.handler;

import com.anan.rbac.model.response.ResponseData;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器
 */
public class CustomLoginAuthSuccessHandler implements AuthenticationSuccessHandler{

    private RequestCache requestCache = new HttpSessionRequestCache();
    private String authSuccessUrl = "index.html";

    /**
     * 登录成功后处理
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Map<String, String> data = new HashMap<>();
        data.put("index", request.getContextPath() + authSuccessUrl);

        ResponseData baseMessage = new ResponseData(ResponseData.SUCCESS, "验证成功", data);

        requestCache.removeRequest(request, response);
        clearAuthenticationAttributes(request);

        response.sendRedirect(authSuccessUrl);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
    public String getAuthSuccessUrl() {
        return authSuccessUrl;
    }
    public void setAuthSuccessUrl(String authSuccessUrl) {
        this.authSuccessUrl = authSuccessUrl;
    }


}
