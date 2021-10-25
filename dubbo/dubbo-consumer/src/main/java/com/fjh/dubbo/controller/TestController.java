package com.fjh.dubbo.controller;

import com.fjh.dubbo.api.DubboTestApi;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("test")
@Api(tags = "测试")
public class TestController {

    @DubboReference(interfaceClass = DubboTestApi.class,check = false)
    private DubboTestApi dubboTestApi;

    @Autowired
    private GenericService genericService;

    @GetMapping("getName")
    public Object getName(String name) {
       Object o =genericService.$invoke("getName", new String[]{"java.lang.String"}, new Object[]{name});
        Map<String, Object> m = RpcContext.getServerContext().getObjectAttachments();
        System.out.println("-------------callback:"+m.toString());
        return  m;
    }


}
