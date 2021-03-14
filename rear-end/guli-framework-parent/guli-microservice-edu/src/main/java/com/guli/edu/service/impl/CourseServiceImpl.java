package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.dto.CourseFrontWebDto;
import com.guli.edu.entity.dto.CourseInfoDto;
import com.guli.edu.entity.form.CourseInfoForm;
import com.guli.edu.entity.query.QueryCourse;
import com.guli.edu.handler.EduException;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存课程信息到课程基础信息表中
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        int result = baseMapper.insert(course);
        if(result == 0){
            throw new EduException(20001,"添加课程信息失败");
        }
        //保存课程描述信息到课程描述信息表中
        CourseDescription cd = new CourseDescription();
        cd.setId(course.getId());
        cd.setDescription(courseInfoForm.getDescription());
        boolean save = courseDescriptionService.save(cd);
        if (!save){
            throw new EduException(20001,"课程详情信息保存失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoById(String courseId) {
        //根据课程id判断数据库是否存在课程
        Course course = baseMapper.selectById(courseId);
        if (course == null) {
            throw new EduException(20001,"没有相关课程信息");
        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course,courseInfoForm);

        //获取课程描述等信息
        CourseDescription byId = courseDescriptionService.getById(courseId);
        courseInfoForm.setDescription(byId.getDescription());
        return courseInfoForm;
    }

    @Override
    public boolean updateCourseInfo(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        int num = baseMapper.updateById(course);
        if(num <= 0){
            throw new EduException(20001,"修改课程基础信息失败");
        }
        CourseDescription cd = new CourseDescription();
        cd.setId(courseInfoForm.getId());
        cd.setDescription(courseInfoForm.getDescription());
        boolean b = courseDescriptionService.updateById(cd);
        return b;
    }

    @Override
    public void getAllCourseInfoList(Page<Course> page, QueryCourse queryCourse) {
        //按时间倒序排序
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("gmt_create");
        //当没有搜索条件时
        if(queryCourse == null){
            baseMapper.selectPage(page,wrapper);
            return;
        }

        //当有搜索条件时
        //课程标题
        String title = queryCourse.getTitle();
        //讲师
        String teacherId = queryCourse.getTeacherId();
        //一级分类
        String subjectParentId = queryCourse.getSubjectParentId();
        //二级分类
        String subjectId = queryCourse.getSubjectId();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }

        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }

        baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean deleteCourseInfoById(String id) {
        //先删小节 再删章节 然后删课程描述 最后删课程
        //根据课程id删除小节信息
        videoService.deleteVideoByCourseId(id);

        //根据课程id删除章节信息
        chapterService.deleteChapterByCourseId(id);

        //删除课程描述
        courseDescriptionService.removeById(id);

        //删除课程
        Integer result = baseMapper.deleteById(id);

        return null != result && result > 0;
    }

    @Override
    public CourseInfoDto getAllCourseInfo(String courseId) {
        CourseInfoDto allCourseInfo = baseMapper.getAllCourseInfos(courseId);
        return allCourseInfo;
    }

    @Override
    public List<Course> getCourseInfoByTeacherId(String id) {
        QueryWrapper<Course> cqw = new QueryWrapper<>();
        cqw.eq("teacher_id",id);
        List<Course> courses = baseMapper.selectList(cqw);
        return courses;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage) {
        QueryWrapper<Course> cqw = new QueryWrapper<>();
        cqw.orderByDesc("gmt_create");
        baseMapper.selectPage(coursePage,cqw);

        Map<String,Object> map = new HashMap<>();
        map.put("courseList",coursePage.getRecords());
        map.put("total",coursePage.getTotal());//总记录数
        map.put("size",coursePage.getSize());//每页记录数
        map.put("pages",coursePage.getPages());//总页数
        map.put("current",coursePage.getCurrent());//当前页
        map.put("previous",coursePage.hasPrevious());//是否有上一页
        map.put("next",coursePage.hasNext());//是否有下一页

        return map;
    }

    @Override
    public CourseFrontWebDto getFrontWebCourseAll(String courseId) {
        CourseFrontWebDto frontCourse = baseMapper.getFrontWebCourseAll(courseId);
        return frontCourse;
    }
}
