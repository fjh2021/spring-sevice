package com.fjh.security.authentication.listern;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 监听事件
 *
 * @author fanjh37
 * @since 2023/2/4 21:07
 */
@Component
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        System.out.println(successEvent.getAuthentication().getName() + "认证成功");
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureDisabledEvent) {
        System.out.println(failureDisabledEvent.getAuthentication().getName() + "认证失败，失败原因：" + failureDisabledEvent.getException().getMessage());
    }
}
