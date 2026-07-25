package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.VisitLogsMapper;
import com.yupi.springbootinit.model.domain.VisitLogs;
import com.yupi.springbootinit.model.vo.VisitStatsVO;
import com.yupi.springbootinit.service.VisitLogsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
* @author 黎旺
* @description 针对表【visit_logs(访问日志表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class VisitLogsServiceImpl extends ServiceImpl<VisitLogsMapper, VisitLogs>
    implements VisitLogsService {

    @Override
    public void recordVisit(String ip, String userAgent, String pageUrl) {
        VisitLogs log = new VisitLogs();
        log.setIp(ip != null ? ip : "");
        log.setUserAgent(userAgent != null ? userAgent : "");
        log.setPageUrl(pageUrl != null ? pageUrl : "");
        log.setVisitDate(new Date());
        save(log);
    }

    @Override
    public VisitStatsVO getVisitStats() {
        LocalDate now = LocalDate.now();
        Date today = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date monthStart = Date.from(now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Integer todayPv = baseMapper.selectPvByDate(today);
        Integer todayUv = baseMapper.selectUvByDate(today);
        Integer monthPv = baseMapper.selectPvSumSince(monthStart);
        Integer totalPv = baseMapper.selectTotalPv();

        VisitStatsVO vo = new VisitStatsVO();
        vo.setTodayPv(todayPv != null ? todayPv : 0);
        vo.setTodayUv(todayUv != null ? todayUv : 0);
        vo.setMonthPv(monthPv != null ? monthPv : 0);
        vo.setTotalPv(totalPv != null ? totalPv : 0);
        return vo;
    }
}
