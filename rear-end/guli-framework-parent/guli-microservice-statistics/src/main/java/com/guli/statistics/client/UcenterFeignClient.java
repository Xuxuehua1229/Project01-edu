package com.guli.statistics.client;


import com.guli.common.constants.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author snowflake
 * @create 2021-02-02 15:41
 */
@Component
@FeignClient(value = "ucenter-service")
public interface UcenterFeignClient {

    @GetMapping("/ucenter/member/getRegUcenterNum")
    R getRegUcenterNum(@RequestParam String day);
}
