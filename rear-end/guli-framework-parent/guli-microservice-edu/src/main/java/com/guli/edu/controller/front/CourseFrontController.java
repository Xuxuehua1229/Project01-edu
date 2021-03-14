package com.guli.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.R;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.dto.ChapterDto;
import com.guli.edu.entity.dto.CourseFrontWebDto;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author snowflake
 * @create 2021-02-07 18:07
 */
@Api(tags = "前台课程controller")
@RestController
@RequestMapping("/edu/courseFront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 获取课程分页信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("getCourseInfoPage/{currentPage}/{pageSize}")
    public R getCourseInfoPage(@PathVariable Long currentPage,
                               @PathVariable Long pageSize){
        Page<Course> coursePage = new Page<>(currentPage,pageSize);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage);
        return R.ok().data(map);
    }

    /**
     * 获取课程相关的所有信息（讲师，章节，小节等信息）
     * @param id
     * @return
     */
    @GetMapping("getCourseInfoAll/{id}")
    public R getCourseInfoAll(@PathVariable String id){
        //获取课程、课程描述、讲师等信息
        CourseFrontWebDto courseDto = courseService.getFrontWebCourseAll(id);


        //根据课程id获取所有章节对应的小节
        List<ChapterDto> chapterDtos = chapterService.getChapterAndVideoByCourseId(id);

        return R.ok().data("courseDto",courseDto).data("chapterDtos",chapterDtos);
    }
}
