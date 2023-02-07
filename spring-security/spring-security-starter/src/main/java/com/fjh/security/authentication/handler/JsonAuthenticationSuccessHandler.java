package com.fjh.security.authentication.handler;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fjh.security.authentication.service.AuthSuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 登录成功处理
 *
 * @author fanjh37
 * @since 2023/2/6 19:09
 */
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired(required = false)
    private AuthSuccessService authSuccessService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        String token = httpServletRequest.getSession().getId();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "登录成功");
        jsonObject.put("token", token);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(jsonObject));
        if (Objects.nonNull(authSuccessService)) {
            authSuccessService.handle(httpServletRequest,httpServletResponse,authentication);
        }
    }
}
