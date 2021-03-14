package com.guli.edu.service;

import com.guli.common.constants.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author snowflake
 * @create 2021-01-31 19:19
 */
@FeignClient("guli-vod")
@Service
public interface VodService {

    @DeleteMapping(value = "/vod/deleteAliYunVideo/{videoSourceId}")
    R removeAliYunVideo(@PathVariable String videoSourceId);

    @DeleteMapping(value = "/vod/deleteMoreVideo")
    R removeMoreAliYunVideo(@RequestParam List<String> videoIds);
}
