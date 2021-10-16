package com.fjh.shiro.starter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author fjh
 * @Email
 * @Date 2021/7/15 17:35
 * @Description
 **/
public class CorsAuthenticationFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        ;
        if (WebUtils.toHttp(servletRequest).getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }
        Subject subject = this.getSubject(servletRequest, servletResponse);
        return subject.isAuthenticated() && subject.getPrincipal() != null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", 401);
        map.put("msg", "未登录");
        writer.write(JSONObject.toJSONString(map));
        writer.close();
        return false;
    }
}
