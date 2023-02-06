package com.fjh.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fjh.security.authentication.filter.SecurityCaptchaFilter;
import com.fjh.security.userdetails.MyJdbcDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.sql.DataSource;

/**
 * @author fanjh37
 * @since 2023/1/31 19:34
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityCaptchaFilter securityCaptchaFilter;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Value("${spring.session.headerName:X-Auth-Token}")
    private String tokenHeader;

    @Value("${spring.security.ignore:}")
    private String[] securityIgnore;

    private String[] defaultIgnore = {"/doc.html", "/v2/**", "/webjars/**", "/swagger-resources/**", "/error", "/captchaImage"};


    /**
     * 用户登录默认
     */
    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        MyJdbcDaoImpl myJdbcDao = new MyJdbcDaoImpl();
        myJdbcDao.setDataSource(dataSource);
        return myJdbcDao;
    }

    /**
     * spring session 序列化
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 请求头 sessionId
     */
    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        return new HeaderHttpSessionIdResolver(tokenHeader);
    }

    /**
     * 忽略路径
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不需要鉴权的路径
        web.ignoring().antMatchers(defaultIgnore).antMatchers(securityIgnore);
        super.configure(web);
    }

    /**
     * 开启是否提示用户名不存在
     *
     * @param userDetailsService
     * @return
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }


    /**
     * 1、配置登录和拦截
     * 2、新增过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录成功
        http.formLogin().successHandler(authenticationSuccessHandler);
        //登录失败
        http.formLogin().failureHandler(authenticationFailureHandler);
        //没有登录权限
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        //没有接口权限 @preAuth
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        http.addFilterBefore(securityCaptchaFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.headers().cacheControl();
    }


}