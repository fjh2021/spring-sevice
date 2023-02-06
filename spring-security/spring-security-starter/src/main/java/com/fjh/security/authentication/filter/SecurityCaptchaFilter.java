package com.fjh.security.authentication.filter;

import com.alicp.jetcache.Cache;
import com.fjh.security.authentication.Exception.KaptchaNotFoundException;
import com.fjh.security.kaptcha.KaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author fanjh37
 * @since 2023/2/6 18:07
 */
@Component
public class SecurityCaptchaFilter extends OncePerRequestFilter {

    private static String defaultFilterProcessUrl = "/login";

    private static String method = "POST";

    @Autowired
    private KaptchaService kaptchaService;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (method.equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            String captchaKey = request.getParameter("captchaKey");
            String captchaValue = request.getParameter("captchaValue");
            Boolean verify = kaptchaService.verify(captchaKey, captchaValue);
            if (!verify) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new KaptchaNotFoundException("验证码错误"));
                return;
            }
            kaptchaService.removeByKey(captchaKey);
        }
        filterChain.doFilter(request, response);
    }
}
