package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.query.QueryTeacher;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void getTeacherWithPageAndCondition(Page<Teacher> page, QueryTeacher queryTeacher) {
        //没有一个查询添加即queryTeacher为null时
        if(queryTeacher == null){
            baseMapper.selectPage(page,null);
            return;
        }

        //有一个或多个查询条件时
        String name = queryTeacher.getName();
        String level = queryTeacher.getLevel();
        String beginDate = queryTeacher.getBeginDate();
        String endDate = queryTeacher.getEndDate();

        //拼接条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        if (StringUtils.hasText(name)){
            wrapper.like("name",name);
        }
        if (StringUtils.hasText(level)){
            wrapper.eq("level",level);
        }
        if (StringUtils.hasText(beginDate)){
            wrapper.gt("gmt_create",beginDate);
        }
        if (StringUtils.hasText(endDate)){
            wrapper.lt("gmt_create",beginDate);
        }

        baseMapper.selectPage(page,wrapper);

    }

    @Override
    public boolean deleteTeacher(String id) {
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public Map<String, Object> getTeacherFrontPage(Page<Teacher> teacherPage) {
        QueryWrapper<Teacher> tqw = new QueryWrapper<>();
        tqw.orderByDesc("gmt_create");//排序（降序）

        //调用数据库获取讲师分页的方法
        baseMapper.selectPage(teacherPage,tqw);

        //获取讲师
        List<Teacher> records = teacherPage.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("teacherList",records);
        map.put("total",teacherPage.getTotal());//总记录数
        map.put("size",teacherPage.getSize());//每页记录数
        map.put("pages",teacherPage.getPages());//总页数
        map.put("current",teacherPage.getCurrent());//当前页
        map.put("previous",teacherPage.hasPrevious());//是否有上一页
        map.put("next",teacherPage.hasNext());//是否有下一页

        return map;
    }
}
