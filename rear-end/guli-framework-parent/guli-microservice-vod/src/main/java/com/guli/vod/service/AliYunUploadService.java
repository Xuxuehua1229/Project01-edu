package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author snowflake
 * @create 2021-01-30 17:37
 */
public interface AliYunUploadService {
    //上传阿里云视频
    String uploadVideoToAliYun(MultipartFile file);

    //删除阿里云视频
    void deleteAliYunVideoById(String videoSourceId);

    //删除多个视频
    void deleteMoreVideoByIds(List videoIds);

    //获取视频播放凭证
    String getVideoPlayAuth(String videoId);
}
