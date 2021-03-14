package com.guli.edu.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author snowflake
 * @create 2021-01-26 22:45
 */
@Data
public class VideoDto implements Serializable {
    @ApiModelProperty(value = "章节ID")
    private String id;
    @ApiModelProperty(value = "章节名称")
    private String title;
    @ApiModelProperty(value = "是否免费试听")
    private String free;
    @ApiModelProperty(value = "视频id")
    private String videoSourceId;

}
