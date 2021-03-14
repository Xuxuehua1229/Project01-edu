package com.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.vod.service.AliYunUploadService;
import com.guli.vod.utils.ALiYunVodSDKUtils;
import com.guli.vod.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author snowflake
 * @create 2021-01-30 17:39
 */
@Service
public class AliYunUploadServiceImpl implements AliYunUploadService {
    @Override
    public String uploadVideoToAliYun(MultipartFile file) {
        String videoId = "";
        try {
            //文件名(视频名称 + 后缀名 1111.mp4)
            String fileName = file.getOriginalFilename();
            //文件名称(视频名称  1111)
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESSKEYID, ConstantPropertiesUtil.ACCESSKEYSECRET,
                    title, fileName, file.getInputStream());
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                //System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                //System.out.print("VideoId=" + response.getVideoId() + "\n");
                //System.out.print("ErrorCode=" + response.getCode() + "\n");
                //System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return videoId;
    }

    @Override
    public void deleteAliYunVideoById(String videoSourceId) {
        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESSKEYID, ConstantPropertiesUtil.ACCESSKEYSECRET);
            //DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoSourceId);
            client.getAcsResponse(request);
        }catch (Exception e){
             e.printStackTrace();
        }
    }

    @Override
    public void deleteMoreVideoByIds(List videoList) {
        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESSKEYID, ConstantPropertiesUtil.ACCESSKEYSECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            //将集合转换成 1,2,3,.... 这种格式
            String videoIds = StringUtils.join(videoList.toArray(), ",");
            //删除多个视频
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getVideoPlayAuth(String videoId) {
        String playAuth = "";
        try {
            //获取阿里云存储相关常量
            String accessKeyId = ConstantPropertiesUtil.ACCESSKEYID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESSKEYSECRET;
            //初始化
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //得到播放凭证
            playAuth = response.getPlayAuth();
        }catch (Exception e){
            e.printStackTrace();
        }
        return playAuth;
    }
}
