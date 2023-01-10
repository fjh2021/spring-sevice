package com.fjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/3/30 10:43
 */
@SpringBootApplication
@EnableCaching
public class FastApp {

    public static void main(String[] args) {
        SpringApplication.run(FastApp.class, args);
    }
}
