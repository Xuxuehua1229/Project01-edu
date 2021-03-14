package com.guli.edu.entity.dto;

import com.guli.edu.entity.Subject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author snowflake
 * @create 2021-01-21 14:44
 */
@Data
public class SubjectDto extends Subject {

    private List<SubjectDto> children = new ArrayList<>();
}
