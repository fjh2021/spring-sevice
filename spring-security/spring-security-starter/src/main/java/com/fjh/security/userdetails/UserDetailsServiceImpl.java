package com.fjh.security.userdetails;

/**
 * @author fanjh37
 * @since 2023/1/31 19:36
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * 样例，参考
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        //1、通过用户查询用户信息
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode("123");
        //找不到抛异常
        //throw new UsernameNotFoundException("", new Object[]{username}, "Username {0} not found"));

        //2、查询权限,查数据库
        String[] authorityList = {"getCurrentUser", "getById"};
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.createAuthorityList(authorityList);

        return new User(name, password, true, true, true, true, grantedAuthorityList);
    }

}