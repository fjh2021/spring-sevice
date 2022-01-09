package com.fjh.email.controller;

import com.fjh.email.service.MailSendService;
import io.swagger.annotations.*;
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
    private MailSendService mailSendService;

    @GetMapping("sendMessage")
    @ApiOperation(value = "发消息", notes = "邮件消息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "message", value = "消息文本",required = true),
            @ApiImplicitParam( name = "subject", value = "主题", required = true)
    })
    String sendMessage1( String message, String subject) {
        mailSendService.simpleMailSend("1731970489@qq.com", subject, message);
        return "消息1:" + message;
    }
}
