package com.fjh.security.config;


import com.fjh.security.authentication.UserLoginFilter;
import com.fjh.security.userdetails.MyJdbcDaoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

/**
 * @author fanjh37
 * @since 2023/1/31 19:34
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${spring.session.headerName:X-Auth-Token}")
    private String tokenHeader;

    @Value("${spring.security.ignore:}")
    private String[] securityIgnore;

    private String[] defaultIgnore = {"/doc.html", "/v2/**", "/webjars/**", "/swagger-resources/**", "/error", "/captchaImage"};

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        MyJdbcDaoImpl myJdbcDao = new MyJdbcDaoImpl();
        myJdbcDao.setDataSource(dataSource);
        return myJdbcDao;
    }

    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        return new HeaderHttpSessionIdResolver(tokenHeader);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不需要鉴权的路径
        web.ignoring().antMatchers(defaultIgnore).antMatchers(securityIgnore);
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UserLoginFilter userLoginFilter = new UserLoginFilter(super.authenticationManager());
        // 登录成功
        userLoginFilter.setAuthenticationSuccessHandler((req, resp, auth) -> {
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String token = req.getSession().getId();
            resp.getWriter().write("{\"code\": 0, \"token\": \"" + token + "\"}");
        });
        // 登录失败
        userLoginFilter.setAuthenticationFailureHandler((req, resp, auth) -> {
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.getWriter().write("{\"code\": 1, \"message\": \"" + auth.getMessage() + "\"}");
        });
        http.logout().logoutUrl("/logout").logoutSuccessHandler((req, resp, auth) -> {
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.getWriter().println("{\"code\":0}");
        });
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.addFilterBefore(userLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }
}