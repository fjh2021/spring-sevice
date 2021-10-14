package com.fjh.shiro;

import com.fjh.shiro.starter.User;
import com.fjh.shiro.starter.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author 范建豪
 * @Email
 * @Date 2021/7/13 17:54
 * @Description
 **/
@Service
public class UserServiceImpl implements UserService {
    /**
     * 获取用户信息
     *
     * @param name
     * @return
     */
    @Override
    public User findByUsername(String name) {
        User user= new User();
        user.setUsername("fjh");
        user.setPassword("65f042b4fbb5c870d0dddd951de2cadb");
        user.setCredentialsSalt("gNHWBMbdthj/riu+UArXAQ==");
        user.setLocked(false);
        return user;
    }

    @Override
    public Set<String> findRoles(String username) {
        Set<String>  role= new HashSet<>();
        role.add("角色1");
        return role;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String>  permissions= new HashSet<>();
        permissions.add("read");
        return permissions;
    }
}
