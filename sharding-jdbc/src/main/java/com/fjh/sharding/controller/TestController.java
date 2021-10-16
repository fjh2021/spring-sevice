package com.fjh.sharding.controller;

import com.fjh.sharding.dao.OrderDao;
import com.fjh.sharding.dao.entity.UserEntity;
import com.fjh.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author fjh
 * @Email
 * @Date 2021/8/24 22:37
 * @Description
 **/
@Controller
public class TestController {


    @Autowired
    private UserService userService;

    @Autowired
    private OrderDao orderDao;

    @GetMapping("get")
    @ResponseBody
    public List<UserEntity> get() {
        return userService.getUserEntity();
    }

}
