package com.guli.edu.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 在启动服务器时，通过该类读取配置文件内容
 * @author snowflake
 * @create 2021-01-15 17:15
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    //服务器启动时候，ConstantPropertiesUtil 初始化，调用里面的 afterPropertiesSet 读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

    public static String ENDPOINT;
    public static String ACCESSKEYID;
    public static String ACCESSKEYSECRET;
    public static String BUCKETNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ACCESSKEYID = accessKeyId;
        ACCESSKEYSECRET = accessKeySecret;
        BUCKETNAME = bucketName;
    }
}
