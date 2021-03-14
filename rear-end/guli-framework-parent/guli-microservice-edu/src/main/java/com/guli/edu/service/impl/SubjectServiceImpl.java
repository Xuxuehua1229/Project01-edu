package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.dto.SubjectDto;
import com.guli.edu.handler.EduException;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2020-12-29
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<String> addSubjectByPoi(MultipartFile file) {
        List<String> list = new ArrayList<>();
        try {
            //1.获取 file 文件流
            InputStream in = file.getInputStream();

            //2.根据文件流创建workbook
            Workbook workbook = new XSSFWorkbook(in);

            //3.根据workbook获取sheet
            Sheet sheetAt = workbook.getSheetAt(0);
            int rowCount = sheetAt.getPhysicalNumberOfRows();
            if(rowCount <= 1){
                list.add("请填写数据！");
                return list;
            }

            //4.根据sheet获取相应的所有行
            //获取Excel中内容的最后一行的下标
            int lastRowNum = sheetAt.getLastRowNum();
            //第一行的内容不需要获取，因此从第二行开始 即下标为1开始
            for (int i = 1; i < lastRowNum; i++) {
                Row row = sheetAt.getRow(i);

                if(row == null){//行为空,做出提示 并且继续执行下面的代码
                    list.add("第" + i + "行为空！");
                    continue;
                }

                //5.根据行获对应的cell 并存储到数据库
                //获取一级分类
                Cell oneCell = row.getCell(0);
                if(oneCell == null){//列为空
                    list.add("第" + i + "行一级分类为null");
                    continue;
                }
                String stringCellValue = oneCell.getStringCellValue();
                //判断要添加的一级分类数据在数据库是否存在
                String pId = getCellValue(stringCellValue, "0");

                //获取二级分类
                Cell twoCell = row.getCell(1);
                if(twoCell == null){
                    list.add("第" + i + "行二级分类为null");
                    continue;
                }
                String stringCellValue1 = twoCell.getStringCellValue();
                //判断要添加的二级分类数据在数据库是否存在
                if(StringUtils.isNotEmpty(pId)){
                    getCellValue(stringCellValue1, pId);
                }
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(20001,"文件导入异常");
        }
    }

    @Override
    public List<SubjectDto> getSubjectInfos() {
        //存储所有分类（包括一级分类和二级分类）
        List<SubjectDto> subjectDtos = new ArrayList<>();

        //获取所有的一级分类
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<Subject> firstLevelSub = baseMapper.selectList(wrapper);
        for (Subject subject : firstLevelSub) {
            SubjectDto subjectDto = new SubjectDto();
            BeanUtils.copyProperties(subject,subjectDto);
            subjectDtos.add(subjectDto);

            //根据一级分类获取所有对应的二级分类
            List<SubjectDto> twoSubjectDtos = new ArrayList<>();
            QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id",subjectDto.getId());
            List<Subject> secondLevelSub = baseMapper.selectList(wrapper1);
            secondLevelSub.stream().forEach(subject1 -> {
                SubjectDto subDto = new SubjectDto();
                BeanUtils.copyProperties(subject1,subDto);
                twoSubjectDtos.add(subDto);
            });
            subjectDto.setChildren(twoSubjectDtos);
        }
        return subjectDtos;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        Integer result = 0;
        //判断一级分类下是否有二级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return false;
        }else{
            result = baseMapper.deleteById(id);
            return null != result && result > 0;
        }
    }

    @Override
    public boolean addOneLevelSubject(Subject subject) {
        //判断要添加的分类是否在数据库存在
        Subject s = getSubjectByTitleAndParentId(subject.getTitle(), "0");
        if(s == null){
            subject.setParentId("0");
            int result = baseMapper.insert(subject);
            return result > 0;
        }
        return false;
    }

    @Override
    public boolean addTwoLevelSubject(Subject subject) {
        //判断要添加的分类是否在数据库存在
        Subject s = getSubjectByTitleAndParentId(subject.getTitle(), subject.getParentId());
        if(s == null){
            int result = baseMapper.insert(subject);
            return result > 0;
        }
        return false;
    }

    public String getCellValue(String stringCellValue,String parentId){
        String pId = null;
        Subject subject = null;
        Subject oneSubject = getSubjectByTitleAndParentId(stringCellValue, parentId);
        if(oneSubject == null){
            subject = new Subject();
            subject.setTitle(stringCellValue);
            subject.setParentId(parentId);

            //调用数据库保存一级分类的方法
            baseMapper.insert(subject);
            pId = subject.getId();
        } else {
            pId = oneSubject.getId();
        }
        return pId;
    }

    public Subject getSubjectByTitleAndParentId(String title,String parentId){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",parentId);
        Subject subject = baseMapper.selectOne(wrapper);
        return subject;
    }
}
