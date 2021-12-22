package com.fjh.rocketapi.config;

import com.github.alenfive.rocketapi.extend.IUserAuthorization;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2021/12/8 21:55
 */
@Component
public class DefaultUserAuthorization implements IUserAuthorization {

    @Override
    public String validate(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "";
        }
        if ("admin".equals(username) && "admin@123".equals(password)) {
            return username;
        }
        return "";
    }
}