package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.R;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.dto.CourseInfoDto;
import com.guli.edu.entity.form.CourseInfoForm;
import com.guli.edu.entity.query.QueryCourse;
import com.guli.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/edu/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    /**
     * 发布课程
     * @param courseId
     * @return
     */
    @GetMapping("updateCoursePublish/{courseId}")
    public R updateCoursePublish(@PathVariable String courseId){
        Course course = courseService.getById(courseId);
        course.setStatus("Normal");
        boolean b = courseService.updateById(course);
        if(b) return R.ok();
        else return R.error();
    }
    /**
     * 获取课程、课程描述、课程分类等信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoDto allCourseInfo = courseService.getAllCourseInfo(courseId);
        return R.ok().data("courseInfo",allCourseInfo);
    }
    /**
     * 保存课程信息
     * @param courseInfoForm
     * @return
     */
    @PostMapping("saveCourseInfo")
    public R insertCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }

    /**
     * 根据课程id获取课程信息
     * @param courseId 课程Id
     * @return
     */
    @GetMapping("getCourseById/{courseId}")
    public R getCourseById(@PathVariable("courseId") String courseId){
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    /**
     * 修改课程信息
     * @param courseInfoForm
     * @return
     */
    @PutMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        boolean result = courseService.updateCourseInfo(courseInfoForm);
        if(result) return R.ok();
        else return R.error();
    }

    /**
     * 获取所有的课程（带分页以及查询条件）
     * @param queryCourse
     * @return
     */
    @PostMapping("getAllCourseInfo")
    public R getAllCourseWithCondition(@RequestParam(required = false) Long currentPage,
                                       @RequestParam(required = false) Long pageSize,
                                       @RequestBody(required = false) QueryCourse queryCourse){
        Page<Course> cPage = new Page<>(currentPage,pageSize);

        courseService.getAllCourseInfoList(cPage,queryCourse);

        long total = cPage.getTotal();

        List<Course> courses = cPage.getRecords();

        return R.ok().data("total",total).data("courses",courses);

    }

    /**
     * 删除课程信息
     * @param id
     * @return
     */
    @DeleteMapping("deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id){
        boolean flag = courseService.deleteCourseInfoById(id);
        if(flag) return R.ok();
        else return R.error();
    }
}

