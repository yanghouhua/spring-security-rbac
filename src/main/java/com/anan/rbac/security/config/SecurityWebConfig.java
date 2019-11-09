package com.anan.rbac.security.config;

import com.anan.rbac.security.auth.CustomAccessDecisionManager;
import com.anan.rbac.security.auth.CustomMetadataSourceService;
import com.anan.rbac.security.filter.CustomLoginFilter;
import com.anan.rbac.security.filter.CustomSecurityInterceptor;
import com.anan.rbac.security.handler.CustomLoginAuthFailureHandler;
import com.anan.rbac.security.handler.CustomLoginAuthSuccessHandler;
import com.anan.rbac.security.handler.CustomLogoutSuccessHandler;
import com.anan.rbac.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置
 */
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter{
    private final Logger log = LoggerFactory.getLogger(SecurityWebConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PropertySource propertySourceBean;

    @Autowired
    private CustomMetadataSourceService customMetadataSourceService;

    /**
     * HttpSecurity安全配置
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("权限框架配置");

        //设置不拦截
        String[] paths = null;
        if (propertySourceBean.getProperty("security.ignoring") != null) {
            paths = propertySourceBean.getProperty("security.ignoring").toString().split(",");
            paths = StringUtil.clearSpace(paths);
        }

        // 设置安全过滤器
        http.authorizeRequests().antMatchers(paths).permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(getCustomLoginAuthEntryPoint())
                .and()
                .addFilterAt(getCustomLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getCustomSecurityInterceptor(), FilterSecurityInterceptor.class)
                .logout().logoutSuccessHandler(getCustomLogoutSuccessHandler())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login.ftl")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();


        log.debug("配置忽略验证url");


    }

    /**
     * 构建认证方式
     * @param auth
     * @throws Exception
     */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDaoAuthenticationProvider());
    }

    /**
     * 处理匿名用户访问无权限资源时的异常
     * @return
     */
    @Bean
    public CustomLoginAuthEntryPoint getCustomLoginAuthEntryPoint() {
        return new CustomLoginAuthEntryPoint();
    }

    /**
     * 登陆
     * @return
     */
    @Bean
    public CustomLoginFilter getCustomLoginFilter() {
        CustomLoginFilter filter = new CustomLoginFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(getCustomLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(getCustomLoginAuthFailureHandler());

        return filter;
    }

    /**
     * 授权过滤器
     * @return
     */
    @Bean
    public CustomSecurityInterceptor getCustomSecurityInterceptor() {
        CustomSecurityInterceptor interceptor = new CustomSecurityInterceptor();

        interceptor.setAccessDecisionManager(getCustomAccessDecisionManager());
        interceptor.setSecurityMetadataSource(getCustomMetadataSourceService());

        try {
            interceptor.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interceptor;
    }


    //===================================================
    /**
     * 用户验证:
     *      1.AuthenticationProvider:认证方式（默认是根据用户名称和密码），如果需要其它
     *      认证方式，可以扩展
     *      2.UserDetailsService：认证数据来源
     * @return
     */
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    /**
     * 登录成功处理器
     * @return
     */
    @Bean
    public CustomLoginAuthSuccessHandler getCustomLoginAuthSuccessHandler() {
        CustomLoginAuthSuccessHandler handler =  new CustomLoginAuthSuccessHandler();
        if (propertySourceBean.getProperty("security.successUrl")!=null){
            handler.setAuthSuccessUrl(propertySourceBean.getProperty("security.successUrl").toString());
        }
        return handler;
    }

    /**
     * 登录失败处理器
     * @return
     */
    @Bean
    public CustomLoginAuthFailureHandler getCustomLoginAuthFailureHandler() {
        return new CustomLoginAuthFailureHandler();
    }

    /**
     * 登出成功处理器
     * @return
     */
    @Bean
    public CustomLogoutSuccessHandler getCustomLogoutSuccessHandler() {
        CustomLogoutSuccessHandler handler = new CustomLogoutSuccessHandler();
        if (propertySourceBean.getProperty("security.logoutSuccessUrl")!=null){
            handler.setLoginUrl(propertySourceBean.getProperty("security.logoutSuccessUrl").toString());
        }
        return handler;
    }

    /**
     * 获取请求资源url权限
     * @return
     */
    @Bean
    public CustomMetadataSourceService getCustomMetadataSourceService() {
        CustomMetadataSourceService sourceService = customMetadataSourceService;//new CustomMetadataSourceService();
        if (propertySourceBean.getProperty("security.successUrl")!=null){
            sourceService.setIndexUrl(propertySourceBean.getProperty("security.successUrl").toString());
        }
        return sourceService;
    }

    /**
     * 验证url需要的权限
     * @return
     */
    @Bean
    public CustomAccessDecisionManager getCustomAccessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

}
