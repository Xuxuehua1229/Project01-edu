package com.guli.edu.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author snowflake
 * @create 2021-01-26 22:44
 */
@Data
public class ChapterDto implements Serializable {
    @ApiModelProperty(value = "章节ID")
    private String id;
    @ApiModelProperty(value = "章节名称")
    private String title;
    @ApiModelProperty(value = "小节")
    private List<VideoDto> children = new ArrayList<>();
}
