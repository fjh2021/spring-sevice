package com.fjh.security.authentication.handler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 登录成功 返回值
 *
 * @author fanjh37
 * @since 2023/2/3 11:20
 */
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String token = httpServletRequest.getSession().getId();
        httpServletResponse.getWriter().write("{\"code\": 0, \"token\": \"" + token + "\"}");
    }


}
