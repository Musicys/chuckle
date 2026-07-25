package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.domain.DailyStats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 黎旺
* @description 针对表【daily_stats(每日统计汇总表)】的数据库操作Mapper
* @createDate 2026-07-07 14:07:09
* @Entity com.yupi.springbootinit.model.domain.DailyStats
*/
public interface DailyStatsMapper extends BaseMapper<DailyStats> {

    /**
     * 查询指定日期范围的PV总和
     */
    Integer selectPvSumSince(@Param("startDate") Date startDate);

    /**
     * 查询总PV
     */
    Integer selectTotalPv();
}
