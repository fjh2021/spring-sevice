package com.fjh.security.util;

import com.fjh.security.entity.User;
import com.fjh.security.service.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fanjh37
 * @since 2023/1/31 19:39
 */
public class HttpSessionUtil {
    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails != null) {
            return userDetails.getUser();
        }
        return null;
    }
}
