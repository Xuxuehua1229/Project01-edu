package com.guli.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author snowflake
 * @create 2021-02-02 15:35
 */
@SpringBootApplication
@EnableFeignClients
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
