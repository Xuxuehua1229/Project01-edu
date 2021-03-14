package com.guli.statistics.service;

import com.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-02
 */
public interface DailyService extends IService<Daily> {

    //将获取到的一天注册用户量保存到数据库中
    boolean saveStatisticsCount(String day);

    //根据查询条件获取统计数据
    Map<String, Object> getStatisticsListByTypeAndDay(String type, String begin, String end);
}
