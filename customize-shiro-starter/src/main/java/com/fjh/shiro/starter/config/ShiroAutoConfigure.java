package com.fjh.shiro.starter.config;

import com.fjh.shiro.starter.CorsAuthenticationFilter;
import com.fjh.shiro.starter.LoginController;
import com.fjh.shiro.starter.ShiroDbRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author fjh
 * @Email
 * @Date 2021/7/14 14:01
 * @Description
 **/
@Configuration
public class ShiroAutoConfigure {

    Logger log = LoggerFactory.getLogger(ShiroAutoConfigure.class);

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.timeout:1800}")
    private int timeout;

    @Value("${shiro.ignore:login}")
    private List<String> shiroIgnore;


    @Bean
    public Realm shiroDbRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
        shiroDbRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroDbRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // need to accept POSTs from the login form
        chainDefinition.addPathDefinition("/logout", "logout");
        shiroIgnore.forEach(x-> chainDefinition.addPathDefinition(x, "anon"));
        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");
        chainDefinition.addPathDefinition("/v2/**", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/configuration/security", "anon");
        chainDefinition.addPathDefinition("/configuration/ui", "anon");
//        chainDefinition.addPathDefinition("/**", "corsAuthenticationFilter");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }


    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        log.info("redis:host" + host);
        redisManager.setHost(host + ":" + port);
        redisManager.setTimeout(timeout);
        if (!StringUtils.isEmpty(password)) {
            redisManager.setPassword(password);
        }
        return redisManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginController loginController(){
        return new LoginController();
    }

    @Bean
    public CorsAuthenticationFilter corsAuthenticationFilter(){
        return new  CorsAuthenticationFilter();
    }

}
