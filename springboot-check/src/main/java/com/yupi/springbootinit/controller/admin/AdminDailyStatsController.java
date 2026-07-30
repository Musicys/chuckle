package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.DailyStats;
import com.yupi.springbootinit.model.dto.dailyStats.DailyStatsQueryRequest;
import com.yupi.springbootinit.service.DailyStatsService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/daily-stats")
@Api(tags = "管理端-每日统计")
public class AdminDailyStatsController {

    @Resource
    private DailyStatsService dailyStatsService;

    private void checkAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!JwtUtils.verify(authHeader.substring(7))) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询每日统计列表")
    public BaseResponse<Page<DailyStats>> listDailyStats(@RequestBody DailyStatsQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<DailyStats> wrapper = new LambdaQueryWrapper<>();
        if (request.getStartDate() != null) {
            wrapper.ge(DailyStats::getStatDate, request.getStartDate());
        }
        if (request.getEndDate() != null) {
            wrapper.le(DailyStats::getStatDate, request.getEndDate());
        }
        wrapper.orderByDesc(DailyStats::getStatDate);

        Page<DailyStats> page = dailyStatsService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        return ResultUtils.success(page);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取单条统计详情")
    public BaseResponse<DailyStats> getDailyStats(@RequestParam("id") Integer id, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "统计ID不能为空");
        }

        DailyStats stats = dailyStatsService.getById(id);
        if (stats == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(stats);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除统计记录")
    public BaseResponse<Boolean> deleteDailyStats(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "统计ID不能为空");
        }

        DailyStats stats = dailyStatsService.getById(request.getId());
        if (stats == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = dailyStatsService.removeById(request.getId());
        return ResultUtils.success(removed);
    }

    @GetMapping("/summary")
    @ApiOperation(value = "获取统计汇总")
    public BaseResponse<Map<String, Object>> getSummary(HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<DailyStats> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(DailyStats::getStatDate);
        wrapper.last("LIMIT 1");
        DailyStats latest = dailyStatsService.getOne(wrapper, false);

        Integer totalPv = dailyStatsService.lambdaQuery()
                .select(DailyStats::getPv)
                .list()
                .stream()
                .map(DailyStats::getPv)
                .filter(java.util.Objects::nonNull)
                .reduce(0, Integer::sum);

        Integer totalUv = dailyStatsService.lambdaQuery()
                .select(DailyStats::getUv)
                .list()
                .stream()
                .map(DailyStats::getUv)
                .filter(java.util.Objects::nonNull)
                .reduce(0, Integer::sum);

        Integer todayPv = 0;
        Integer todayUv = 0;
        if (latest != null) {
            todayPv = latest.getPv() != null ? latest.getPv() : 0;
            todayUv = latest.getUv() != null ? latest.getUv() : 0;
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalPv", totalPv);
        summary.put("totalUv", totalUv);
        summary.put("todayPv", todayPv);
        summary.put("todayUv", todayUv);

        return ResultUtils.success(summary);
    }
}
