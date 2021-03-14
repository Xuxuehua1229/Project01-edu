package com.guli.edu.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author snowflake
 * @create 2021-01-28 17:18
 */
@Data
public class CourseInfoDto {
    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程封面")
    private String cover;
    @ApiModelProperty(value = "课程价格")
    private String price;
    @ApiModelProperty(value = "课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程描述")
    private String description;
    @ApiModelProperty(value = "讲师名称")
    private String teacherName;
    @ApiModelProperty(value = "一级分类名称")
    private String oneLevelSubName;
    @ApiModelProperty(value = "二级分类名称")
    private String twoLevelSubName;

}
