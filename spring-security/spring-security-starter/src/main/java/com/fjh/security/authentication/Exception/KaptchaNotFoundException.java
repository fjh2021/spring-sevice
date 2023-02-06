package com.fjh.security.authentication.Exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author fanjh37
 * @since 2023/2/6 18:48
 */
public class KaptchaNotFoundException extends AuthenticationException {
    public KaptchaNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public KaptchaNotFoundException(String msg) {
        super(msg);
    }
}
