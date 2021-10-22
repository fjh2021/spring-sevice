package com.fjh.dubbo.provider;

import com.fjh.dubbo.api.DubboTestApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DubboTestService implements DubboTestApi {
    @Override
    public String getName(String name) {
        System.out.println("---------------name:"+name);
        return "name:"+name;
    }
}
