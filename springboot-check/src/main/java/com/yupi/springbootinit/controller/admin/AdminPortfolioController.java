package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Images;
import com.yupi.springbootinit.model.dto.portfolio.PortfolioAddRequest;
import com.yupi.springbootinit.model.dto.portfolio.PortfolioQueryRequest;
import com.yupi.springbootinit.model.dto.portfolio.PortfolioUpdateRequest;
import com.yupi.springbootinit.service.PortfolioService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/portfolio")
@Api(tags = "管理端-作品管理")
public class AdminPortfolioController {

    @Resource
    private PortfolioService portfolioService;

    private void checkAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!JwtUtils.verify(authHeader.substring(7))) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增作品")
    public BaseResponse<Integer> addPortfolio(@RequestBody PortfolioAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getTitle())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作品名称不能为空");
        }

        Images.Portfolio entity = new Images.Portfolio();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setCategory(request.getCategory() != null ? request.getCategory() : "");
        entity.setCover(request.getCover() != null ? request.getCover() : "");
        entity.setProjectUrl(request.getProjectUrl() != null ? request.getProjectUrl() : "");
        entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        entity.setStatus(request.getStatus() != null ? request.getStatus() : 1);
        portfolioService.save(entity);

        return ResultUtils.success(entity.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新作品")
    public BaseResponse<Boolean> updatePortfolio(@RequestBody PortfolioUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作品ID不能为空");
        }

        Images.Portfolio entity = portfolioService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (StringUtils.hasText(request.getTitle())) entity.setTitle(request.getTitle());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getCategory() != null) entity.setCategory(request.getCategory());
        if (request.getCover() != null) entity.setCover(request.getCover());
        if (request.getProjectUrl() != null) entity.setProjectUrl(request.getProjectUrl());
        if (request.getSortOrder() != null) entity.setSortOrder(request.getSortOrder());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());

        boolean updated = portfolioService.updateById(entity);
        return ResultUtils.success(updated);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询作品列表（管理端）")
    public BaseResponse<Page<Images.Portfolio>> listPortfolio(@RequestBody PortfolioQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<Images.Portfolio> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getTitle())) {
            wrapper.like(Images.Portfolio::getTitle, request.getTitle());
        }
        if (StringUtils.hasText(request.getCategory())) {
            wrapper.like(Images.Portfolio::getCategory, request.getCategory());
        }
        if (request.getStatus() != null) {
            wrapper.eq(Images.Portfolio::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(Images.Portfolio::getSortOrder);

        Page<Images.Portfolio> page = portfolioService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        return ResultUtils.success(page);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除作品")
    public BaseResponse<Boolean> deletePortfolio(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作品ID不能为空");
        }

        Images.Portfolio entity = portfolioService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = portfolioService.removeById(request.getId());
        return ResultUtils.success(removed);
    }

    @GetMapping("/all")
    @ApiOperation(value = "获取所有作品（管理端编辑时用）")
    public BaseResponse<List<Images.Portfolio>> getAllPortfolio(HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        List<Images.Portfolio> list = portfolioService.lambdaQuery()
                .orderByAsc(Images.Portfolio::getSortOrder)
                .list();
        return ResultUtils.success(list);
    }
}
