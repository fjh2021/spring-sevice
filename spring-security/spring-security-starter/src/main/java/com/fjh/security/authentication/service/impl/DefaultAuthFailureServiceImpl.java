package com.fjh.security.authentication.service.impl;

import com.alicp.jetcache.Cache;
import com.fjh.security.authentication.service.AuthFailureService;
import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.inmemory.request.InMemorySlidingWindowRequestRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author fanjh37
 * @since 2023/2/6 22:46
 */
@Slf4j
public class DefaultAuthFailureServiceImpl implements AuthFailureService {

    Set<RequestLimitRule> rules =
            Collections.singleton(RequestLimitRule.of(1 * 60, TimeUnit.MINUTES, 5));
    RequestRateLimiter limiter = new InMemorySlidingWindowRequestRateLimiter(rules);

    @Override
    public void authenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String key = httpServletRequest.getParameter("username");
        //计数器加1，并判断该用户是否已经到了触发了锁定规则
        boolean reachLimit = limiter.overLimitWhenIncremented(key);
        log.info("reachLimit is :{}",reachLimit);
    }
}
