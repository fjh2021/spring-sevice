package com.fjh.dubbo.api;

import com.fjh.dubbo.dto.User;

public interface DubboTestApi {

    User getName(User user);

    String logoutCallback(String token);

}
