package com.guli.edu.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author snowflake
 * @create 2021-01-24 21:34
 */
@Data
public class QueryCourse implements Serializable {
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "讲师id")
    private String teacherId;
    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
}
