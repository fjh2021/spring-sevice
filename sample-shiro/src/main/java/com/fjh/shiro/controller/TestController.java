package com.fjh.shiro.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 范建豪
 * @Email
 * @Date 2021/7/28 14:19
 * @Description
 **/
@RestController
@RequestMapping
@Api
public class TestController {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四"),
            @ApiImplicitParam(name = "address", value = "用户地址", defaultValue = "深圳", required = true)
    })
    @GetMapping("getTest")
    @ApiOperation("测试")
    public Map get(String username, String address) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "fss");
        return map;
    }
}
