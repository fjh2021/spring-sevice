package com.fjh.security.authentication.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fanjh37
 * @since 2023/2/6 21:17
 */
public interface AuthSuccessService {
    /**
     * 处理事件
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication e) throws IOException, ServletException;
}