package com.fjh.mq.controller;

import com.fjh.mq.dto.MessageDto;
import com.fjh.mq.service.PulsarProducerService;
import com.fjh.mq.service.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/21 12:55
 */
@RestController
@Api(tags = "pulsar")
public class PulsarController {

    @Autowired
    private PulsarProducerService pulsarProducer;

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult sendMessage(@RequestBody MessageDto message) {
        pulsarProducer.send(message);
        return ResponseResult.success(message);
    }
}
