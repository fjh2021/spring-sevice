package com.fjh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/2/11 9:33
 */
@SpringBootApplication
@MapperScan("com.fjh.mapper")
public class H2App {
    public static void main(String[] args) {
        SpringApplication.run(H2App.class, args);
    }
}
