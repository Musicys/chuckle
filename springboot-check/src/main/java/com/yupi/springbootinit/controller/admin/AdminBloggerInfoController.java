package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.BloggerInfo;
import com.yupi.springbootinit.model.dto.blogger.BloggerAddRequest;
import com.yupi.springbootinit.model.dto.blogger.BloggerQueryRequest;
import com.yupi.springbootinit.model.dto.blogger.BloggerUpdateRequest;
import com.yupi.springbootinit.model.vo.BloggerInfoVO;
import com.yupi.springbootinit.service.BloggerInfoService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/blogger")
@Api(tags = "管理端-博主信息管理")
public class AdminBloggerInfoController {

    @Resource
    private BloggerInfoService bloggerInfoService;

    /**
     * 校验管理员登录状态
     */
    private void checkAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String token = authHeader.substring(7);
        if (!JwtUtils.verify(token)) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增博主信息")
    public BaseResponse<Integer> addBlogger(@RequestBody BloggerAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getNickname())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "昵称不能为空");
        }

        BloggerInfo info = new BloggerInfo();
        BeanUtils.copyProperties(request, info);
        bloggerInfoService.save(info);

        return ResultUtils.success(info.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新博主信息")
    public BaseResponse<Boolean> updateBlogger(@RequestBody BloggerUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        Integer id = request.getId();
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "ID不能为空");
        }

        BloggerInfo info = bloggerInfoService.getById(id);
        if (info == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        BeanUtils.copyProperties(request, info);
        boolean updated = bloggerInfoService.updateById(info);
        return ResultUtils.success(updated);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取博主信息详情（管理端）")
    public BaseResponse<BloggerInfo> getBlogger(@RequestParam Integer id, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        BloggerInfo info = bloggerInfoService.getById(id);
        if (info == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(info);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询博主信息列表（管理端）")
    public BaseResponse<Page<BloggerInfoVO>> listBloggers(@RequestBody BloggerQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<BloggerInfo> wrapper = new LambdaQueryWrapper<>();
        if (request.getId() != null) {
            wrapper.eq(BloggerInfo::getId, request.getId());
        }
        if (StringUtils.hasText(request.getNickname())) {
            wrapper.like(BloggerInfo::getNickname, request.getNickname());
        }
        if (StringUtils.hasText(request.getBlogTitle())) {
            wrapper.like(BloggerInfo::getBlogTitle, request.getBlogTitle());
        }
        if (request.getIsUsed() != null) {
            wrapper.eq(BloggerInfo::getIsUsed, request.getIsUsed());
        }
        wrapper.orderByDesc(BloggerInfo::getCreatedAt);

        Page<BloggerInfo> page = bloggerInfoService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        Page<BloggerInfoVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<BloggerInfoVO> records = page.getRecords().stream().map(info -> {
            BloggerInfoVO vo = new BloggerInfoVO();
            BeanUtils.copyProperties(info, vo);
            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(records);
        return ResultUtils.success(voPage);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除博主信息")
    public BaseResponse<Boolean> deleteBlogger(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "ID不能为空");
        }

        BloggerInfo info = bloggerInfoService.getById(request.getId());
        if (info == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = bloggerInfoService.removeById(request.getId());
        return ResultUtils.success(removed);
    }

    @PostMapping("/setUsed")
    @ApiOperation(value = "切换配置使用状态（仅一条记录为启用）")
    public BaseResponse<Boolean> setUsed(@RequestParam Integer id, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        BloggerInfo info = bloggerInfoService.getById(id);
        if (info == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "博主信息不存在");
        }

        bloggerInfoService.setUsed(id);
        return ResultUtils.success(true);
    }
}
