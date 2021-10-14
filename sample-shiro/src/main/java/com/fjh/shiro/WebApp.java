
package com.fjh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ControllerAdvice
@SpringBootApplication
public class WebApp { //NOPMD

    private static Logger log = LoggerFactory.getLogger(WebApp.class);

    public static void main(String[] args) {

        SpringApplication.run(WebApp.class, args);
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(AuthorizationException e, Model model) {

        // you could return a 404 here instead (this is how github handles 403, so the user does NOT know there is a
        // resource at that location)
        log.debug("AuthorizationException was thrown", e);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "No message available");
        model.addAttribute("errors", map);

        return "error";
    }

//    @Bean
//    public Realm realm() {
//        TextConfigurationRealm realm = new TextConfigurationRealm();
//        realm.setUserDefinitions("joe.coder=password,user\n" +
//                "jill.coder=password,admin");
//
//        realm.setRoleDefinitions("admin=read,write\n" +
//                "user=read");
//        realm.setCachingEnabled(true);
//        return realm;
//    }



    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }
}
