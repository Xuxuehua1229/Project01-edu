package com.guli.ucenter.service.impl;

import com.guli.ucenter.entity.Member;
import com.guli.ucenter.mapper.MemberMapper;
import com.guli.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-01
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Integer getRegisteredNumber(String day) {
        //获取一天用户注册的人数
        Integer ucenterNum = baseMapper.getUcenterNum(day);
        return ucenterNum;
    }
}
