package com.fjh.shiro.starter;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author fjh
 * @Email
 * @Date 2021/7/14 11:09
 * @Description
 **/
public class PasswordUtil {

    public static String md5(String username, String password) {
        String algorithmName = "md5";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toString();
        System.out.println("salt2:" + salt2);
        int hashIterations = 2;
        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        return hash.toString();
    }

    public static void main(String[] args) {
        String username = "fjh";
        String password = "123";
        String en = md5(username, password);
        System.out.println("en:" + en);
    }
}
