package com.fjh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/alter")
public class AlertController {


    @RequestMapping(value = "/send", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String pstn(@RequestBody String json) {
        log.info("------------\n alert notify  params: {}", json);
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "报警失败");
        result.put("code", 0);

        if (StringUtils.isBlank(json)) {
            return JSON.toJSONString(result);
        }
        JSONObject jo = JSON.parseObject(json);

        JSONObject commonAnnotations = jo.getJSONObject("commonAnnotations");
        String status = jo.getString("status");
        if (commonAnnotations == null) {
            return JSON.toJSONString(result);
        }


        String subject = commonAnnotations.getString("summary");
        String content = commonAnnotations.getString("description");
        List<String> emailusers = new ArrayList<>();
        emailusers.add("xxx@aliyun.com");

        List<String> users = new ArrayList<>();
        users.add("158*****5043");


        try {
            boolean success = true;
//            boolean success = Util.email(subject, content, emailusers);
            if (success) {
                result.put("msg", "报警成功");
                result.put("code", 1);
            }
        } catch (Exception e) {
            log.error("=alert email notify error. json={}", json, e);
        }
        try {
//            boolean success = Util.sms(subject, content, users);
            boolean success = true;
            if (success) {
                result.put("msg", "报警成功");
                result.put("code", 1);
            }
        } catch (Exception e) {
            log.error("=alert sms notify error. json={}", json, e);
        }


        return JSON.toJSONString(result);
    }

}

