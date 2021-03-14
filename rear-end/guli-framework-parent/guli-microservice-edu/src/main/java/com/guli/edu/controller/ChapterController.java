package com.guli.edu.controller;


import com.guli.common.constants.R;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.dto.ChapterDto;
import com.guli.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 删除章节
     * @param id
     * @return
     */
    @DeleteMapping("deleteChapter/{id}")
    public R deleteChapterByChapterId(@PathVariable String id){
        boolean b = chapterService.deleteChapterByChapterId(id);
        if(b) return R.ok();
        else return R.error();
    }

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PutMapping("updateChapter")
    public R updateChapterInfoById(@RequestBody Chapter chapter){
        boolean b = chapterService.updateById(chapter);
        if(b) return R.ok();
        else return R.error();
    }

    /**
     * 获取章节信息
     * @param id
     * @return
     */
    @GetMapping("getChapterById/{id}")
    public R getChapterInfoById(@PathVariable String id){
        Chapter byId = chapterService.getById(id);
        return R.ok().data("chapter",byId);
    }

    /**
     * 添加章节信息
     * @param chapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapterInfo(@RequestBody Chapter chapter){
        boolean result = chapterService.addChapter(chapter);
        if(result) return R.ok();
        else return R.error();
    }

    /**
     * 获取章节和对应的小节
     * @return
     */
    @GetMapping("getChapterAndVideoInfo/{id}")
    public R getChapterAndVideoInfo(@PathVariable String id){
        List<ChapterDto> chapterDtos = chapterService.getChapterAndVideoByCourseId(id);
        return R.ok().data("items",chapterDtos);
    }
}

