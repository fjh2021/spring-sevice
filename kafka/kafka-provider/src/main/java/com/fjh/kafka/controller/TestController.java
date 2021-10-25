package com.fjh.kafka.controller;

import com.fjh.kafka.service.IndicatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
@Api(tags = "kafka")
public class TestController {

    @Autowired
    private IndicatorService indicatorService;

    @GetMapping("message")
    public void setMessage(String topic,String message) {
        indicatorService.sendMessage(topic,message);
    }
}
