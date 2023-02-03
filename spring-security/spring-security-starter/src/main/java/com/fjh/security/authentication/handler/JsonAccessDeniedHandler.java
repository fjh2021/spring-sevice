package com.fjh.security.authentication.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 没有权限
 *
 * @author fanjh37
 * @since 2023/2/3 12:50
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    @Value("${spring.security.deniedCode:403}")
    private Integer deniedCode;

    @Value("${spring.security.deniedMsg:没有权限}")
    private String deniedMsg;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.getWriter().write("{\"code\": 403, \"message\": \"" + "没有权限" + "\"}");
    }
}
