package com.guli.vod.controller;

import com.guli.common.constants.R;
import com.guli.vod.service.AliYunUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author snowflake
 * @create 2021-01-30 17:32
 */
@RestController
@RequestMapping("/vod")
public class ALiYunUploadController {
    @Autowired
    private AliYunUploadService aliYunUploadService;

    /**
     * 获取视频播放凭证
     * @param videoId
     * @return
     */
    @GetMapping("getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable String videoId){
        String playAuth = aliYunUploadService.getVideoPlayAuth(videoId);
        return R.ok().message("获取视频播放凭证成功").data("playAuth",playAuth);
    }

    /**
     * 删除多个视频
     * @param videoIds
     * @return
     */
    @DeleteMapping("deleteMoreVideo")
    public R deleteMoreAliYunVideo(@RequestParam List videoIds){
        aliYunUploadService.deleteMoreVideoByIds(videoIds);
        return R.ok().message("删除成功");
    }
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("video/upload")
    public R uploadVideoALiYun(@RequestPart MultipartFile file){
        String videoId = aliYunUploadService.uploadVideoToAliYun(file);
        return R.ok().message("上传视频成功").data("videoId",videoId);
    }

    /**
     * 删除阿里云视频
     * @param videoSourceId 阿里云视频id
     * @return
     */
    @DeleteMapping("deleteAliYunVideo/{videoSourceId}")
    public R deleteAliYunVideo(@PathVariable String videoSourceId){
        aliYunUploadService.deleteAliYunVideoById(videoSourceId);
        return R.ok().message("删除成功！");
    }
}
