package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.constants.R;
import com.guli.statistics.client.UcenterFeignClient;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.mapper.DailyMapper;
import com.guli.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author SnowFlake
 * @since 2021-02-02
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    private UcenterFeignClient ucenterFeignClient;

    @Override
    public boolean saveStatisticsCount(String day) {
        //由于每个时刻查询的用户注册数量不一样，所以如果表中已经存在过该天的注册量，则要进行覆盖
        //通过统计日期查询是否已经存在该天的注册量，存在则删除重新更新注册用户量
        QueryWrapper<Daily> dqw = new QueryWrapper<>();
        dqw.eq("date_calculated",day);
        //删除
        baseMapper.delete(dqw);

        //通过服务调用（feign）获取一天用户注册量
        R regNum = ucenterFeignClient.getRegUcenterNum(day);
        Map<String, Object> data = regNum.getData();
        Integer registeredNumber = (Integer) data.get("registeredNumber");

        //创建统计对象
        Daily daily = new Daily();
        //统计日期
        daily.setDateCalculated(day);
        //注册人数
        daily.setRegisterNum(registeredNumber);
        //登录人数
        daily.setLoginNum(RandomUtils.nextInt(100,200));//TODO
        //每日播放视频数
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));//TODO
        //每日新增课程数
        daily.setCourseNum(RandomUtils.nextInt(100,200));//TODO

        //保存统计数据
        int result = baseMapper.insert(daily);


        return result > 0;
    }

    @Override
    public Map<String, Object> getStatisticsListByTypeAndDay(String type, String begin, String end) {
        QueryWrapper<Daily> dqw = new QueryWrapper<>();
        dqw.between("date_calculated",begin,end);
        dqw.select("date_calculated",type);//只查询 date_calculated 和 type 的值的两个字段

        //创建一个集合存储统计时间
        List<String> dateList = new ArrayList<>();
        //创建一个集合存储统计数量
        List<Integer> dataList = new ArrayList<>();
        //根据条件获取数据
        List<Daily> dailies = baseMapper.selectList(dqw);
        dailies.stream().forEach(daily -> {
            //存储统计时间
            dateList.add(daily.getDateCalculated());

            //存储统计数量
            //首先根据 type 判断要获取的是什么类型的统计数量 比如：注册数量，还是登录数量等
            switch (type){
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        });

        //将两个数据的集合存储到map中
        Map<String,Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("dataList",dataList);

        return map;
    }
}
