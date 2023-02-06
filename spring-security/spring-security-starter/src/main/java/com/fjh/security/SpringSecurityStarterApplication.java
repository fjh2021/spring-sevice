package com.fjh.security;

import com.fjh.security.util.HttpSessionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSecurityStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityStarterApplication.class, args);
    }

    @GetMapping("getCurrentUser")
    @PreAuthorize("hasAuthority('getCurrentUser')")
    public UserDetails getCurrentUser() {
        return HttpSessionUtil.getUser();
    }

}
