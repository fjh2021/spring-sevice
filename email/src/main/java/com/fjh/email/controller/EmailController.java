package com.fjh.email.controller;

import com.fjh.email.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/5 15:56
 */
@Api(tags = "邮件")
@RestController
public class EmailController {

    @Autowired
    private MailService mailService;

    @GetMapping("sendMessage")
    @ApiOperation(value = "发消息", notes = "邮件消息")
    @ApiParam(value = "message", name = "message")
    String sendMessage(String message) {
        mailService.senMail();
        return "消息:" + message;
    }
}
