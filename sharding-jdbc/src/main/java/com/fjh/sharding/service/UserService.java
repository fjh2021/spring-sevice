package com.fjh.sharding.service;

import com.fjh.sharding.dao.UserMapper;
import com.fjh.sharding.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范建豪
 * @Email
 * @Date 2021/8/24 22:39
 * @Description
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<UserEntity> getUserEntity() {
        return userMapper.getUserEntity();
    }
}
