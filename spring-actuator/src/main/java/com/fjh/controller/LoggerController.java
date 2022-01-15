package com.fjh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/13 22:18
 */
@Api(tags = "监控日志")
@RestController
@Slf4j
public class LoggerController {

    @GetMapping("test")
    @ApiOperation("测试")
    public String logger(String message) {
        log.info("info:{}", message);
        log.error("error:{}", message);
        log.debug("debug:{】", message);
        return "test"+message;
    }
}
