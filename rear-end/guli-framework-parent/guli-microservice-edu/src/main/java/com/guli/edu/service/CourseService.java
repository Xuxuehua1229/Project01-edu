package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.dto.CourseFrontWebDto;
import com.guli.edu.entity.dto.CourseInfoDto;
import com.guli.edu.entity.form.CourseInfoForm;
import com.guli.edu.entity.query.QueryCourse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
public interface CourseService extends IService<Course> {
    //保存课程信息
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    //根据课程id获取课程信息
    CourseInfoForm getCourseInfoById(String courseId);

    //修改课程信息
    boolean updateCourseInfo(CourseInfoForm courseInfoForm);

    //获取课程信息分页以及带条件
    void getAllCourseInfoList(Page<Course> cPage, QueryCourse queryCourse);

    //删除课程信息
    boolean deleteCourseInfoById(String id);

    //获取课程、课程描述、分类信息
    CourseInfoDto getAllCourseInfo(String courseId);

    //根据讲师id获取对应讲师的所有课程
    List<Course> getCourseInfoByTeacherId(String id);

    //获取课程分页信息
    Map<String, Object> getCourseFrontList(Page<Course> coursePage);

    //获取前台网页课程详情
    CourseFrontWebDto getFrontWebCourseAll(String courseId);
}
