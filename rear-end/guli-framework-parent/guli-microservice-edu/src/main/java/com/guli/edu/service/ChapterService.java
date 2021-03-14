package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.dto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
public interface ChapterService extends IService<Chapter> {
    //根据课程id删除章节信息
    boolean deleteChapterByCourseId(String id);

    //获取章节和对应的小节
    List<ChapterDto> getChapterAndVideoByCourseId(String id);

    //添加章节信息
    boolean addChapter(Chapter chapter);

    //根据章节id删除章节信息
    boolean deleteChapterByChapterId(String id);
}
