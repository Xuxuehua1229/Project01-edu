package com.guli.statistics.controller;


import com.guli.common.constants.R;
import com.guli.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {
   @Autowired
   private DailyService dailyService;

    /**
     * 保存一天用户注册量
     * @param day
     * @return
     */
   @GetMapping("createStatisticsByDay/{day}")
   public R createStatisticsByDay(@PathVariable String day){
       boolean b = dailyService.saveStatisticsCount(day);
       if (b) return R.ok();
       else return R.error();
   }

    /**
     * 根据查询条件获取统计数据
     * @param type 统计因子，即需要获取什么类型的数据统计（如：学员登录数统计 或者 学员注册数统计等）
     * @param begin 开始时间
     * @param end 结束时间
     * @return
     */
   @GetMapping("getStatisticsByTypeAndDay/{type}/{begin}/{end}")
   public R getStatisticsByTypeAndDay(@PathVariable String type,
                                      @PathVariable String begin,
                                      @PathVariable String end){
       Map<String,Object> statistics = dailyService.getStatisticsListByTypeAndDay(type,begin,end);
       return R.ok().data("statistics",statistics);
   }
}

