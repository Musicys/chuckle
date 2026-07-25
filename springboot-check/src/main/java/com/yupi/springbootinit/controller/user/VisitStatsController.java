package com.yupi.springbootinit.controller.user;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.vo.VisitStatsVO;
import com.yupi.springbootinit.service.VisitLogsService;
import com.yupi.springbootinit.utils.NetUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/visits")
@Api(tags = "访问统计")
public class VisitStatsController {

    @Resource
    private VisitLogsService visitLogsService;

    @PostMapping("/record")
    @ApiOperation(value = "记录访问（前端页面加载时调用，自动记录IP和UA）")
    public BaseResponse<Void> recordVisit(@RequestBody(required = false) String pageUrl,
                                          HttpServletRequest request) {
        String ip = NetUtils.getIpAddress(request);
        String ua = request.getHeader("User-Agent");
        visitLogsService.recordVisit(ip, ua, pageUrl);
        return ResultUtils.success(null);
    }

    @GetMapping("/stats")
    @ApiOperation(value = "获取访问统计（今日PV/UV、本月PV、总PV，实时从访问日志聚合）")
    public BaseResponse<VisitStatsVO> getVisitStats() {
        VisitStatsVO vo = visitLogsService.getVisitStats();
        return ResultUtils.success(vo);
    }
}
