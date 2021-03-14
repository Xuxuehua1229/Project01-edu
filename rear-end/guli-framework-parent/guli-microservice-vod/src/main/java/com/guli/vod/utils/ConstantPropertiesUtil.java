package com.guli.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author snowflake
 * @create 2021-01-30 17:41
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${aliyun.vod.file.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.vod.file.accessKeySecret}")
    private String accessKeySecret;

    public static String ACCESSKEYID;
    public static String ACCESSKEYSECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESSKEYID = accessKeyId;
        ACCESSKEYSECRET = accessKeySecret;
    }
}
