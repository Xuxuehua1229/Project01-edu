package com.guli.edu.controller;


import com.guli.common.constants.R;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.dto.SubjectDto;
import com.guli.edu.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    /**
     * 读取Excel文件内容，将内容存储到数据库
     * @param file
     * @return
     */
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("import")
    public R addSubjectByImportPoi(@RequestPart("file") MultipartFile file){
        List<String> msg = subjectService.addSubjectByPoi(file);
        if(msg.size() == 0){
            return R.ok().message("批量导入成功");
        }else {
            return R.error().message("部分数据导入失败").data("messageList", msg);
        }
    }

    /**
     * 获取所有课程分类信息
     * @return
     */
    @GetMapping("getSubjectList")
    public R getSubjectInfoList(){
        List<SubjectDto> subjectDtos = subjectService.getSubjectInfos();
        return R.ok().data("subjectList",subjectDtos);
    }

    /**
     * 根据课程分类id删除课程分类数据
     * @param id
     * @return
     */
    @DeleteMapping("deleteSubject/{id}")
    public R deleteSubjectInfoById(@PathVariable("id") String id){
        boolean result = subjectService.deleteSubjectById(id);
        if(result) return R.ok();
        else return R.error();
    }

    /**
     * 添加一级分类
     * @param subject
     * @return
     */
    @PostMapping("addOneSubject")
    public R addOneSubject(@RequestBody Subject subject){
        boolean result = subjectService.addOneLevelSubject(subject);
        if(result){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 添加二级分类
     * @param subject
     * @return
     */
    @PostMapping("addTwoSubject")
    public R addTwoSubject(@RequestBody Subject subject){
        boolean result = subjectService.addTwoLevelSubject(subject);
        if(result){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

