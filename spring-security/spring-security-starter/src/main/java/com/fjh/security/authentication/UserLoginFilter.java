package com.fjh.security.authentication;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanjh37
 * @since 2023/1/31 19:35
 */

public class UserLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String USERNAME_PARAMETER = "username";

    private static final String PASSWORD_PARAMETER = "password";


    private static final String CODE_PARAMETER = "code";

    public UserLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", HttpMethod.POST.name(), true));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Method not supported");
        }

//        String code = getCode(request);
//        if (StringUtils.isBlank(code)) {
//            throw new AuthenticationServiceException("验证码为空");
//        }
//        HttpSession session = request.getSession();
//        String sessionCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        if (!code.equals(sessionCode) ) {
//            throw new AuthenticationServiceException("验证码错误");
//        }
        AuthenticationManager authenticationManager = this.getAuthenticationManager();
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(getUsername(request), getPassword(request)));
    }

    private String getUsername(HttpServletRequest request) {
        String username = request.getParameter(USERNAME_PARAMETER);
        if (username == null) {
            username = "";
        }
        return username.trim();
    }

    private String getPassword(HttpServletRequest request) {
        String password = request.getParameter(PASSWORD_PARAMETER);
        if (password == null) {
            password = "";
        }
        return password;
    }

    private String getCode(HttpServletRequest request) {
        return request.getParameter(CODE_PARAMETER);
    }
}