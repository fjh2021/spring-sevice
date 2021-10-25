package com.fjh.dubbo.provider;

import com.cubigdata.wisdomdata.api.LogoutCallbackApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
@Slf4j
public class DubboTestService implements LogoutCallbackApi {

    @Override
    public String logoutCallback(String token) {
        log.info("come in----------------------------------logout callback");
        return "success------" + System.currentTimeMillis();
    }
}
