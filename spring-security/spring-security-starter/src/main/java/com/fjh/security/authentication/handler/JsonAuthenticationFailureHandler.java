package com.fjh.security.authentication.handler;

import com.fjh.security.authentication.exception.UsernameNotFindException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 登录失败
 * @author fanjh37
 * @since 2023/2/3 13:22
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (e.getCause() instanceof UsernameNotFindException) {
            httpServletResponse.getWriter().write("{\"code\": 1, \"message\": \"" + e.getMessage() + "\"}");
            return;
        }

        httpServletResponse.getWriter().write("{\"code\": 2, \"message\": \"" + "密码错误" + "\"}");
    }
}
