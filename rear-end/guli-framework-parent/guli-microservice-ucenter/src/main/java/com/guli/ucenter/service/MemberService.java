package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-01
 */
public interface MemberService extends IService<Member> {
    //获取一天中注册的人数
    Integer getRegisteredNumber(String day);
}
