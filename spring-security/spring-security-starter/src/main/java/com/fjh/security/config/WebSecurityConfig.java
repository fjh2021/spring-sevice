package com.fjh.security.config;


import com.fjh.security.service.MyJdbcDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanjh37
 * @since 2023/1/31 19:34
 */
@Configuration
@EnableWebSecurity
@EnableSpringHttpSession
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        MyJdbcDaoImpl myJdbcDao = new MyJdbcDaoImpl();
        myJdbcDao.setDataSource(dataSource);
        return myJdbcDao;
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }

    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        return new HeaderHttpSessionIdResolver("X-Token");
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
            resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            resp.getWriter().write("{\"code\": 1, \"message\": \"" + auth.getMessage() + "\"}");
        });
        // 不需要鉴权的路径
        http.authorizeRequests().antMatchers("/doc.html", "/v2/**", "/webjars/**", "/swagger-resources/**", "/error", "/captchaImage").permitAll()
                .anyRequest().authenticated();
        http.logout().logoutUrl("/logout").logoutSuccessHandler((req, resp, auth) -> {
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println("{\"code\":0}");
        });
        http.csrf().disable();
        http.addFilterBefore(userLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }
}