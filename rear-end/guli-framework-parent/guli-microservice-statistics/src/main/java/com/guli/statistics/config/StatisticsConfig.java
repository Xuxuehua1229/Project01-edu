package com.guli.statistics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author snowflake
 * @create 2021-02-02 15:53
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.guli.statistics.mapper")
public class StatisticsConfig {
}
