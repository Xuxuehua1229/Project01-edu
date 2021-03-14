package com.guli.ucenter.controller;


import com.guli.common.constants.R;
import com.guli.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("getRegUcenterNum")
    public R getRegUcenterNum(@RequestParam String day){
        Integer registeredNumber = memberService.getRegisteredNumber(day);
        return R.ok().data("registeredNumber",registeredNumber);
    }
}

