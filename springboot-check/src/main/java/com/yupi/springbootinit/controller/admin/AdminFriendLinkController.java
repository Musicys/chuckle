package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.FriendLinks;
import com.yupi.springbootinit.model.dto.friendLink.FriendLinkAddRequest;
import com.yupi.springbootinit.model.dto.friendLink.FriendLinkQueryRequest;
import com.yupi.springbootinit.model.dto.friendLink.FriendLinkUpdateRequest;
import com.yupi.springbootinit.service.FriendLinksService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/friendLink")
@Api(tags = "管理端-友链管理")
public class AdminFriendLinkController {

    @Resource
    private FriendLinksService friendLinksService;

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
    @ApiOperation(value = "新增友链")
    public BaseResponse<Integer> addFriendLink(@RequestBody FriendLinkAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "友站名称不能为空");
        }
        if (!StringUtils.hasText(request.getUrl())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "友站URL不能为空");
        }

        FriendLinks entity = new FriendLinks();
        entity.setName(request.getName());
        entity.setUrl(request.getUrl());
        entity.setAvatar(request.getAvatar() != null ? request.getAvatar() : "");
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setEmail(request.getEmail() != null ? request.getEmail() : "");
        entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        entity.setStatus(request.getStatus() != null ? request.getStatus() : 1);
        friendLinksService.save(entity);

        return ResultUtils.success(entity.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新友链")
    public BaseResponse<Boolean> updateFriendLink(@RequestBody FriendLinkUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "友链ID不能为空");
        }

        FriendLinks entity = friendLinksService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (StringUtils.hasText(request.getName())) entity.setName(request.getName());
        if (StringUtils.hasText(request.getUrl())) entity.setUrl(request.getUrl());
        if (request.getAvatar() != null) entity.setAvatar(request.getAvatar());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getSortOrder() != null) entity.setSortOrder(request.getSortOrder());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());

        boolean updated = friendLinksService.updateById(entity);
        return ResultUtils.success(updated);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询友链列表（管理端）")
    public BaseResponse<Page<FriendLinks>> listFriendLinks(@RequestBody FriendLinkQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<FriendLinks> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getName())) {
            wrapper.like(FriendLinks::getName, request.getName());
        }
        if (request.getStatus() != null) {
            wrapper.eq(FriendLinks::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(FriendLinks::getSortOrder);

        Page<FriendLinks> page = friendLinksService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        return ResultUtils.success(page);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除友链")
    public BaseResponse<Boolean> deleteFriendLink(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "友链ID不能为空");
        }

        FriendLinks entity = friendLinksService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = friendLinksService.removeById(request.getId());
        return ResultUtils.success(removed);
    }

    @GetMapping("/all")
    @ApiOperation(value = "获取所有友链（管理端编辑时用）")
    public BaseResponse<List<FriendLinks>> getAllFriendLinks(HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        List<FriendLinks> list = friendLinksService.lambdaQuery()
                .orderByAsc(FriendLinks::getSortOrder)
                .list();
        return ResultUtils.success(list);
    }
}
