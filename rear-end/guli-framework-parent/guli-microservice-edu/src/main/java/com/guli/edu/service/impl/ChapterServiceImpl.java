package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.entity.dto.ChapterDto;
import com.guli.edu.entity.dto.VideoDto;
import com.guli.edu.handler.EduException;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public boolean deleteChapterByCourseId(String id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        Integer result = baseMapper.delete(wrapper);
        return null != result && result > 0;
    }

    @Override
    public List<ChapterDto> getChapterAndVideoByCourseId(String id) {
        //根据课程Id获取所有的章节
        QueryWrapper<Chapter> cqw = new QueryWrapper<>();
        cqw.eq("course_id",id);
        List<Chapter> chapters = baseMapper.selectList(cqw);

        //根据课程Id获取所有的小节
        QueryWrapper<Video> vqw = new QueryWrapper<>();
        vqw.eq("course_id",id);
        List<Video> videos = videoService.list(vqw);

        //创建章节和小节存储的集合
        List<ChapterDto> chapterDtos = new ArrayList<>();
        //遍历所有的章节
        chapters.stream().forEach(chapter -> {
            //创建ChapterDto对象
            ChapterDto chapterDto = new ChapterDto();
            //将chapter对象的值赋值给chapterDto
            BeanUtils.copyProperties(chapter,chapterDto);
            //将chapter对象添加到集合中
            chapterDtos.add(chapterDto);

            //创建存储小节的集合
            List<VideoDto> videoDtos = new ArrayList<>();
            //遍历所有的小节
            videos.stream().forEach(video -> {
                //判断章节表里的id和小节里的id
                if(chapter.getId().equals(video.getChapterId())){
                    //创建VideoDto对象
                    VideoDto videoDto = new VideoDto();
                    BeanUtils.copyProperties(video,videoDto);
                    videoDtos.add(videoDto);
                }
            });
            //将小节集合存储到章节对象中
            chapterDto.setChildren(videoDtos);
        });
        return chapterDtos;
    }

    @Override
    public boolean addChapter(Chapter chapter) {
        int insert = baseMapper.insert(chapter);
        return insert>0;
    }

    @Override
    public boolean deleteChapterByChapterId(String id) {
        //判断章节是否存在小节
        QueryWrapper<Video> vqw = new QueryWrapper<>();
        vqw.eq("chapter_id",id);
        int count = videoService.count(vqw);
        if(count > 0){
            throw new EduException(20001,"该章节下存在小节，不能删除！");
        }
        //不存在小节，直接删除
        int result = baseMapper.deleteById(id);
        return result > 0;
    }
}
