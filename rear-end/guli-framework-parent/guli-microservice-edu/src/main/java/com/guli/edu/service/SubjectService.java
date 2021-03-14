package com.guli.edu.service;

import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.dto.SubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
public interface SubjectService extends IService<Subject> {
    //将Excel表格了的数据保存到数据库
    List<String> addSubjectByPoi(MultipartFile file);

    //获取课程分类信息
    List<SubjectDto> getSubjectInfos();

    //删除课程分类信息
    boolean deleteSubjectById(String id);

    //添加一级分类
    boolean addOneLevelSubject(Subject subject);

    //添加二级分类
    boolean addTwoLevelSubject(Subject subject);
}
