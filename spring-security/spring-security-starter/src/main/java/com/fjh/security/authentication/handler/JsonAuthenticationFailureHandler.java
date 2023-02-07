package com.fjh.security.authentication.handler;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fjh.security.authentication.Exception.KaptchaNotFoundException;
import com.fjh.security.authentication.service.AuthFailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 登录失败处理
 *
 * @author fanjh37
 * @since 2023/2/6 19:02
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired(required = false)
    private AuthFailureService authFailureService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        if (e instanceof UsernameNotFoundException) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "用户名不存在");
        } else if (e instanceof BadCredentialsException) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "密码错误");
        } else if (e instanceof KaptchaNotFoundException) {
            jsonObject.put("code", 3);
            jsonObject.put("msg", e.getMessage());
        }
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(jsonObject));
        if (Objects.nonNull(authFailureService)) {
            authFailureService.authenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
