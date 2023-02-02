package com.fjh.security.controller;

import com.fjh.security.entity.User;
import com.fjh.security.util.HttpSessionUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanjh37
 * @since 2023/1/31 19:44
 */
@RestController
public class TestController {

    @GetMapping("getNameById")
    @ApiOperation("测试")
    public User getCurrentUser() {
        return HttpSessionUtil.getUser();
    }
}
