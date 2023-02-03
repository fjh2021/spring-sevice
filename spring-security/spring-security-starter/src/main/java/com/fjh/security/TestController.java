package com.fjh.security;

import com.fjh.security.util.HttpSessionUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanjh37
 * @since 2023/1/31 19:44
 */
@RestController
public class TestController {

    @GetMapping("getCurrentUser")
    @PreAuthorize("hasAuthority('getCurrentUser')")
    public UserDetails getCurrentUser() {
        return HttpSessionUtil.getUser();
    }
}
