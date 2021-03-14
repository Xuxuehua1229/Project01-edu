package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.VideoMapper;
import com.guli.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    @Override
    public boolean deleteVideoByCourseId(String courseId) {
        List videoIdList = new ArrayList();
        //根据课程id获取所有的小节的视频
        QueryWrapper<Video> vqw = new QueryWrapper<>();
        vqw.eq("course_id",courseId);
        vqw.select("video_source_id");
        List<Video> videos = baseMapper.selectList(vqw);

        if(videos.size() > 0){
            videos.stream().forEach(video -> {
                videoIdList.add(video.getVideoSourceId());
            });

            if(videoIdList.size() > 0){
                //删除小节视频
                vodService.removeMoreAliYunVideo(videoIdList);
            }
        }

        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        Integer delete = baseMapper.delete(wrapper);
        return null != delete && delete>0;
    }

    @Override
    public boolean deleteVideoByVideoId(String id) {
        //TODO 完善：小节视频 删除阿里云上传的视频
        //首先获取小节视频的id，即阿里云视频的id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //删除阿里云视频
            vodService.removeAliYunVideo(videoSourceId);
        }

        //删除小节表的数据
        int result = baseMapper.deleteById(id);

        return result > 0;
    }
}
