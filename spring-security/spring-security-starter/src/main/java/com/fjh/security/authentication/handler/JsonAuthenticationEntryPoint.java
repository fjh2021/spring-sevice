package com.fjh.security.authentication.handler;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fjh.security.authentication.service.AuthEntryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 没有登录权限处理
 *
 * @author fanjh37
 * @since 2023/2/6 19:08
 */
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired(required = false)
    private AuthEntryPointService authEntryPointService;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("msg", "请先登录");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(jsonObject));
        if (Objects.isNull(authEntryPointService)) {
            authEntryPointService.commence(httpServletRequest,httpServletResponse,e);
        }
    }
}
