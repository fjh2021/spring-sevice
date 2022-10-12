package com.fjh;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @Author fjh
 * @Email
 * @Date 2022/10/1219:41
 * @Description
 **/
@SpringBootApplication
public class CanalClientApp implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalClientApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        canalClient.run();
    }
}
