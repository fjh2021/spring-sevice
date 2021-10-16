package com.fjh.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author fjh
 * @Email
 * @Date 2021/8/24 22:03
 * @Description
 **/
@SpringBootApplication
@MapperScan("com.fjh.sharding.dao")
public class ShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
    }

}
