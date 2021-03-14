package com.guli.edu.mapper;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.dto.CourseFrontWebDto;
import com.guli.edu.entity.dto.CourseInfoDto;
import com.guli.edu.entity.form.CourseInfoForm;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
public interface CourseMapper extends BaseMapper<Course> {
    //获取课程信息
    CourseInfoDto getAllCourseInfos(String courseId);

    //获取前台网页课程详情
    CourseFrontWebDto getFrontWebCourseAll(String courseId);
}
