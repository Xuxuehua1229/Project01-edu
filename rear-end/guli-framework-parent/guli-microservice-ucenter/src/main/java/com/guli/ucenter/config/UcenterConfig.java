package com.guli.ucenter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author snowflake
 * @create 2021-02-02 15:22
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value = "com.guli.ucenter.mapper")
public class UcenterConfig {

}
