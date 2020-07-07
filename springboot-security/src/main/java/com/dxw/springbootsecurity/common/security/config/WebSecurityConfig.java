package com.dxw.springbootsecurity.common.security.config;

import com.dxw.springbootsecurity.common.security.handler.*;
import com.dxw.springbootsecurity.common.security.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * @Author: Dxw
 * @Date: 2020/6/1 11:02
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] MATCHERS_URL = {"/login", "/authentication/require", "/authentication/form",
            "/yz/web/users", "/yz/web/userDetails", "/yz/web/messageSetting"};
    private final FailureHandler failureHandler;
    private final SuccessHandler successHandler;
    private final DxwAccessDeniedHandler accessDeniedHandler;
    private final UserAuthService userAuthService;
    private final DxwSessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    private final UnauthenticatedEntryPoint unauthenticatedEntryPoint;

    public WebSecurityConfig(UserAuthService userAuthService, FailureHandler failureHandler, SuccessHandler successHandler,
                             DxwAccessDeniedHandler accessDeniedHandler, DxwSessionInformationExpiredStrategy sessionInformationExpiredStrategy,
                             UnauthenticatedEntryPoint unauthenticatedEntryPoint) {
        this.userAuthService = userAuthService;
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
        this.unauthenticatedEntryPoint = unauthenticatedEntryPoint;
    }

    /**
     * 注入身份管理器
     *
     * @Author: Dxw
     * @Date: 2020/6/1 11:02
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 注入自定义权限管理
     *
     * @Author: Dxw
     * @Date: 2020/6/1 11:02
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setPermissionEvaluator(new DxwPermissionEvaluator());
        return defaultWebSecurityExpressionHandler;
    }

    /**
     * 加密解密 自定义md5
     *
     * @Author: Dxw
     * @Date: 2020/6/1 11:02
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userAuthService).passwordEncoder(
                new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return charSequence.toString();
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return s.equals(charSequence.toString());
                    }
                }
        );
    }


    /**
     * Security 安全管理器
     *
     * @Author: Dxw
     * @Date: 2020/6/1 11:02
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 注册自定义成功 失败处理器
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .and()
                .formLogin()
                .loginPage("/login")
                // 自定义 登录路径
                .loginProcessingUrl("/authentication/form")
                .and()
                // 对请求授权
                .authorizeRequests()
                // 这些页面不需要身份验证
                .antMatchers(MATCHERS_URL).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthenticatedEntryPoint)
                .and()
                // session 处理
                .sessionManagement()
                .maximumSessions(-1)
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and().and()
                .csrf().disable();

    }

}

