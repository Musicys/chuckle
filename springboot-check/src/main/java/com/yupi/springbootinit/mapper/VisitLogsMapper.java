package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.domain.VisitLogs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 黎旺
* @description 针对表【visit_logs(访问日志表)】的数据库操作Mapper
* @createDate 2026-07-07 14:07:09
* @Entity com.yupi.springbootinit.model.domain.VisitLogs
*/
public interface VisitLogsMapper extends BaseMapper<VisitLogs> {

    /**
     * 查询指定日期的PV
     */
    Integer selectPvByDate(@Param("date") Date date);

    /**
     * 查询指定日期的UV（去重IP）
     */
    Integer selectUvByDate(@Param("date") Date date);

    /**
     * 查询指定日期之后的PV总和
     */
    Integer selectPvSumSince(@Param("startDate") Date startDate);

    /**
     * 查询总PV
     */
    Integer selectTotalPv();
}




