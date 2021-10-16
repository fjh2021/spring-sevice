package com.fjh.shiro.starter;

import com.fjh.shiro.starter.User;

import java.util.Set;

/**
 * @Author fjh
 * @Email
 * @Date 2021/7/13 17:25
 * @Description
 **/
public interface UserService {

    /**
     * 获取用户信息
     *
     * @param name
     * @return
     */
    User findByUsername(String name);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
