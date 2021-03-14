package com.guli.edu.controller;


import com.guli.common.constants.R;
import com.guli.edu.entity.Video;
import com.guli.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@RestController
@RequestMapping("/edu/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 根据小节id删除小节信息
     * @param id
     * @return
     */
    @DeleteMapping("deleteVideoInfo/{id}")
    public R deleteVideoInfo(@PathVariable String id){
        boolean b = videoService.deleteVideoByVideoId(id);
        if(b) return R.ok();
        else return R.error();
    }

    /**
     * 修改小节信息
     * @param video
     * @return
     */
    @PutMapping("updateVideoInfo")
    public R updateVideoInfo(@RequestBody Video video){
        System.out.println(video.getId());
        boolean b = videoService.updateById(video);
        if(b) return R.ok();
        else return R.error();
    }

    /**
     * 获取小节信息
     * @param videoId
     * @return
     */
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }

    /**
     * 添加小节信息
     * @param video
     * @return
     */
    @PostMapping("addVideoInfo")
    public R addVideoInfo(@RequestBody Video video){
        boolean save = videoService.save(video);
        if(save) return R.ok();
        else return R.error();
    }

}

