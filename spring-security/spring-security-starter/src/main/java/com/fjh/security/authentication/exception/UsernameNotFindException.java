package com.fjh.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 账号错误异常
 *
 * @author fanjh37
 * @since 2023/2/3 14:11
 */
public class UsernameNotFindException extends AuthenticationException {
    /**
     * Constructs a <code>UsernameNotFoundException</code> with the specified message.
     *
     * @param msg the detail message.
     */
    public UsernameNotFindException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code UsernameNotFoundException} with the specified message and root
     * cause.
     *
     * @param msg   the detail message.
     * @param cause root cause
     */
    public UsernameNotFindException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
