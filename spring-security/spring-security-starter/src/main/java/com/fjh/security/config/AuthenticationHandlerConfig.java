package com.fjh.security.config;

import com.fjh.security.authentication.handler.JsonAccessDeniedHandler;
import com.fjh.security.authentication.handler.JsonAuthenticationEntryPoint;
import com.fjh.security.authentication.handler.JsonAuthenticationFailureHandler;
import com.fjh.security.authentication.handler.JsonAuthenticationSuccessHandler;
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


    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JsonAuthenticationSuccessHandler();
    }


    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JsonAccessDeniedHandler();
    }
}
