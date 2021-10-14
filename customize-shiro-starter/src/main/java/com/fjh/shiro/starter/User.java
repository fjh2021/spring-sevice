package com.fjh.shiro.starter;

import java.io.Serializable;

/**
 * @Author 范建豪
 * @Email
 * @Date 2021/7/13 17:28
 * @Description
 **/
public class User implements Serializable {

    private String username;

    private String password;

    private boolean locked;

    private String credentialsSalt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getCredentialsSalt() {
        return credentialsSalt;
    }

    public void setCredentialsSalt(String credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }
}
