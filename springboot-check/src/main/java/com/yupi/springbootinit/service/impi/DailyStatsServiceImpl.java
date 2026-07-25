package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.DailyStats;
import com.yupi.springbootinit.service.DailyStatsService;
import com.yupi.springbootinit.mapper.DailyStatsMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【daily_stats(每日统计汇总表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class DailyStatsServiceImpl extends ServiceImpl<DailyStatsMapper, DailyStats>
    implements DailyStatsService{

}
