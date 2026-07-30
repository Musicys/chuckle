package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.VisitLogs;
import com.yupi.springbootinit.model.dto.visitLogs.VisitLogsQueryRequest;
import com.yupi.springbootinit.model.vo.VisitStatsVO;
import com.yupi.springbootinit.service.VisitLogsService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/visit-logs")
@Api(tags = "管理端-访问日志")
public class AdminVisitLogsController {

    @Resource
    private VisitLogsService visitLogsService;

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
    @ApiOperation(value = "分页查询访问日志列表")
    public BaseResponse<Page<VisitLogs>> listVisitLogs(@RequestBody VisitLogsQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<VisitLogs> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getIp())) {
            wrapper.like(VisitLogs::getIp, request.getIp());
        }
        if (StringUtils.hasText(request.getPageUrl())) {
            wrapper.like(VisitLogs::getPageUrl, request.getPageUrl());
        }
        if (request.getStartDate() != null) {
            wrapper.ge(VisitLogs::getVisitDate, request.getStartDate());
        }
        if (request.getEndDate() != null) {
            wrapper.le(VisitLogs::getVisitDate, request.getEndDate());
        }
        wrapper.orderByDesc(VisitLogs::getCreatedAt);

        Page<VisitLogs> page = visitLogsService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        return ResultUtils.success(page);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除访问日志")
    public BaseResponse<Boolean> deleteVisitLogs(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "日志ID不能为空");
        }

        VisitLogs entity = visitLogsService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = visitLogsService.removeById(request.getId());
        return ResultUtils.success(removed);
    }

    @PostMapping("/batch-delete")
    @ApiOperation(value = "批量删除访问日志")
    public BaseResponse<Boolean> batchDeleteVisitLogs(@RequestBody List<Long> ids, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "日志ID列表不能为空");
        }

        boolean removed = visitLogsService.removeByIds(ids);
        return ResultUtils.success(removed);
    }

    @GetMapping("/stats")
    @ApiOperation(value = "获取访问统计汇总")
    public BaseResponse<VisitStatsVO> getVisitStats(HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        VisitStatsVO stats = visitLogsService.getVisitStats();
        return ResultUtils.success(stats);
    }
}
