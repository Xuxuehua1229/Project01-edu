package com.guli.edu.service;

import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
public interface VideoService extends IService<Video> {
    //根据课程id删除小节信息
    boolean deleteVideoByCourseId(String courseId);

    //根据小节id删除小节信息
    boolean deleteVideoByVideoId(String id);
}
