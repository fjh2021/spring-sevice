package com.fjh.security.userdetails;

/**
 * @author fanjh37
 * @since 2023/1/31 19:36
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Resource
//    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
//                .eq(User::getName, name)
//        );
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("用户:%s，不存在", name));
//        }
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode("123");
        log.info("password:{}", password);
        return new User(name, password, true, true, true, true, AuthorityUtils.NO_AUTHORITIES);
    }
}