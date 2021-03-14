package com.guli.ucenter.mapper;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-01
 */
public interface MemberMapper extends BaseMapper<Member> {
    Integer getUcenterNum(String day);
}
