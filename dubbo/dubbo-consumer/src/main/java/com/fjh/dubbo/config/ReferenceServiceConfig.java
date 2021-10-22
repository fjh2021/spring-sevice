package com.fjh.dubbo.config;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceServiceConfig {

    @Bean
    public GenericService genericService() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface("com.fjh.dubbo.api.DubboTestApi");
        reference.setCluster("broadcast2");
        // 声明为泛化接口
        reference.setGeneric("true");
        return reference.get();
    }


}
