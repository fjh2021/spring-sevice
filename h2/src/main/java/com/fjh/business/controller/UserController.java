package com.fjh.business.controller;

import com.fjh.business.mapper.UserMapper;
import com.fjh.model.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/2/11 9:57
 */
@Api(tags = "h2接口")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("test")
    public List<User> testSelect() {
        List<User> userList = userMapper.selectList(null);
        return userList;
    }
}
