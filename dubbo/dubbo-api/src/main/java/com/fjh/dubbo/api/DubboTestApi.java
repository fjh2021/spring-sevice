package com.fjh.dubbo.api;

public interface DubboTestApi {

    String getName(String name);

    String logoutCallback(String token);
}
