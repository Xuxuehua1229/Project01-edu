package com.guli.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.R;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.Teacher;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author snowflake
 * @create 2021-02-05 16:00
 */
@Api(tags = "前台讲师模块")
@RestController
@RequestMapping("/edu/frontTeacher")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;


    /**
     * 获取前台页面讲师分页信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "前台讲师分页数据")
    @GetMapping("{currentPage}/{pageSize}")
    public R getFrontTeacherInfoPage(@PathVariable Long currentPage,
                                    @PathVariable Long pageSize){
        Page<Teacher> teacherPage = new Page<>(currentPage,pageSize);
        Map<String,Object> map = teacherService.getTeacherFrontPage(teacherPage);
        return R.ok().data(map);
    }

    /**
     * 根据讲师id获取讲师对应的所有的课程
     * @param id
     * @return
     */
    @ApiOperation(value = "前台讲师详情及对应所讲课程")
    @GetMapping("getFrontTeacherDetails/{id}")
    public R getFrontTeacherDetails(@PathVariable String id){
        //获取讲师详情
        Teacher teacher = teacherService.getById(id);

        //获取讲师所有的课程
        List<Course> courses = courseService.getCourseInfoByTeacherId(id);

        return R.ok().data("teacher",teacher).data("courses",courses);
    }
}


