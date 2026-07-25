package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.domain.VisitLogs;
import com.yupi.springbootinit.model.vo.VisitStatsVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 黎旺
* @description 针对表【visit_logs(访问日志表)】的数据库操作Service
* @createDate 2026-07-07 14:07:09
*/
public interface VisitLogsService extends IService<VisitLogs> {

    /**
     * 记录访问
     */
    void recordVisit(String ip, String userAgent, String pageUrl);

    /**
     * 获取访问统计（今日PV/UV、本月PV、总PV）
     */
    VisitStatsVO getVisitStats();
}
