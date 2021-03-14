package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.query.QueryTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
public interface TeacherService extends IService<Teacher> {

    void getTeacherWithPageAndCondition(Page<Teacher> page, QueryTeacher queryTeacher);

    boolean deleteTeacher(String id);

    //获取前台讲师分页信息
    Map<String, Object> getTeacherFrontPage(Page<Teacher> teacherPage);
}
