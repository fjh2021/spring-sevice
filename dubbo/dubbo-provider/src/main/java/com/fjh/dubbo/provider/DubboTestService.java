package com.fjh.dubbo.provider;

import com.fjh.dubbo.api.DubboTestApi;
import com.fjh.dubbo.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Date;

@DubboService
@Slf4j
public class DubboTestService implements DubboTestApi {

    @Override
    public User getName(User u) {
        User user = new User();
        user.setName("fjh");
        user.setAge(29);
        user.setTime(new Date());
        user.setId("123");
        log.info("--------------------------------");
        log.info("测试成功");
        log.info("--------------------------------");
        return user;
    }

    @Override
    public String logoutCallback(String token) {
        log.info("come in----------------------------------logout callback");
        return "success------" + System.currentTimeMillis();
    }
}
