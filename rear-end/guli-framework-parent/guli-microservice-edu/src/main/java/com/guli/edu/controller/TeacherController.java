package com.guli.edu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.R;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.query.QueryTeacher;
import com.guli.edu.handler.EduException;
import com.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    /**
     * 模拟登录
     * @return {"code":20000,"data":{"token":"admin-token"}}
     */
    @PostMapping("/user/login")
    public R login(){
        return R.ok().data("token","admin-token");
    }

    /**
     * 获取信息
     * @return {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
     */
    @GetMapping("/user/info")
    public R info(){
        Map<String,Object> map = new HashMap<>();
        map.put("roles","['admin']");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return R.ok().data(map);
    }

    /**
     * 修改讲师信息
     * @param t
     * @return
     */
    @PutMapping("updateTeacherById")
    public R updateTeacherById(@RequestBody Teacher t){
        boolean b = teacherService.updateById(t);
        if (b) return R.ok();
        else return R.error();
    }

    /**
     * 根据讲师id获取讲师信息
     * @param id 讲师id
     * @return
     */
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        Teacher byId = teacherService.getById(id);
        return R.ok().data("items",byId);
    }

    /**
     * 添加讲师数据
     * @param teacher
     * @return
     */
    @PostMapping("saveTeacherInfo")
    public R saveTeacherInfo(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if (save) return R.ok();
        else return R.error();
    }

    /**
     * 获取讲师数据带分页和条件
     * @param currentPage 当前页数
     * @param pageSize  每页记录数
     * @param queryTeacher 条件
     * @return
     */
    @PostMapping("conditionPageTeacher/{currentPage}/{pageSize}")
    public R getConditionPageTeacherList(@PathVariable Long currentPage,
                                         @PathVariable Long pageSize,
                                         @RequestBody(required = false) QueryTeacher queryTeacher){
        Page<Teacher> pageTeacher = new Page<>(currentPage,pageSize);

        teacherService.getTeacherWithPageAndCondition(pageTeacher,queryTeacher);

        long total = pageTeacher.getTotal();

        List<Teacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("items",records);
    }

    /**
     * 分页
     * @param currentPage  当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("/pageTeacherList/{currentPage}/{pageSize}")
    public R getPageTeacherList(@PathVariable Long currentPage,
                                @PathVariable Long pageSize){

        Page<Teacher> pageTeacher = new Page<>(currentPage,pageSize);

        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();

        List<Teacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("items",records);
    }

    /**
     * 获取所有讲师的数据
     * @return
     */
    @GetMapping("/getTeacherList")
    public R getTeacherList(){
        /*try {
            int num = 11/0;
        }catch (Exception e){
            throw new EduException(20001,"执行自定义异常！");
        }*/

        List<Teacher> list = teacherService.list();
        return R.ok().data("items",list);
    }


    /**
     * 根据讲师id删除指定讲师信息
     * @param id 讲师id
     * @return
     */
    @DeleteMapping("deleteTeacherById/{id}")
    public R deleteTeacherById(@PathVariable String id){
        //boolean b = teacherService.removeById(id);
        boolean b = teacherService.deleteTeacher(id);
        if (b){
            return R.ok().data("items",b);
        }else {
            return R.error();
        }
    }
}

