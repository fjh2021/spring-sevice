package com.fjh.security.config;

import com.fjh.security.authentication.handler.JsonAccessDeniedHandler;
import com.fjh.security.authentication.handler.JsonAuthenticationEntryPoint;
import com.fjh.security.authentication.handler.JsonAuthenticationFailureHandler;
import com.fjh.security.authentication.handler.JsonAuthenticationSuccessHandler;
import com.fjh.security.authentication.service.impl.DefaultAuthFailureServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author fanjh37
 * @since 2023/2/6 19:03
 */

@Configuration
public class AuthenticationHandlerConfig {

    /**
     * 登录失败处理
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler();
    }

    /**
     * 登录成功处理
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JsonAuthenticationSuccessHandler();
    }

    /**
     * 没有登录权限处理
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint();
    }

    /**
     * 接口鉴权
     */
    @Bean
    @ConditionalOnMissingBean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JsonAccessDeniedHandler();
    }

    /**
     * 登录失败后，操作
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAuthFailureServiceImpl authFailureService() {
        return new DefaultAuthFailureServiceImpl();
    }
}
