package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Tags;
import com.yupi.springbootinit.model.dto.tag.TagAddRequest;
import com.yupi.springbootinit.model.dto.tag.TagQueryRequest;
import com.yupi.springbootinit.model.dto.tag.TagUpdateRequest;
import com.yupi.springbootinit.model.vo.TagVO;
import com.yupi.springbootinit.service.TagsService;
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
@RequestMapping("/admin/tag")
@Api(tags = "管理端-标签管理")
public class AdminTagController {

    @Resource
    private TagsService tagsService;

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
    @ApiOperation(value = "新增标签")
    public BaseResponse<Integer> addTag(@RequestBody TagAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签名称不能为空");
        }

        // 检查标签名是否已存在
        long count = tagsService.lambdaQuery().eq(Tags::getName, request.getName()).count();
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签名称已存在");
        }

        Tags tag = new Tags();
        tag.setName(request.getName());
        tag.setColor(request.getColor() != null ? request.getColor() : "#409EFF");
        tagsService.save(tag);

        return ResultUtils.success(tag.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新标签")
    public BaseResponse<Boolean> updateTag(@RequestBody TagUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签ID不能为空");
        }

        Tags tag = tagsService.getById(request.getId());
        if (tag == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 如果修改了名称，检查是否与其他标签冲突
        if (StringUtils.hasText(request.getName()) && !request.getName().equals(tag.getName())) {
            long count = tagsService.lambdaQuery().eq(Tags::getName, request.getName()).count();
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签名称已存在");
            }
            tag.setName(request.getName());
        }
        if (request.getColor() != null) {
            tag.setColor(request.getColor());
        }

        boolean updated = tagsService.updateById(tag);
        return ResultUtils.success(updated);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询标签列表（管理端）")
    public BaseResponse<Page<TagVO>> listTags(@RequestBody TagQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<Tags> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getName())) {
            wrapper.like(Tags::getName, request.getName());
        }
        wrapper.orderByDesc(Tags::getCreatedAt);

        Page<Tags> page = tagsService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        Page<TagVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<TagVO> records = page.getRecords().stream().map(tag -> {
            TagVO vo = new TagVO();
            BeanUtils.copyProperties(tag, vo);
            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(records);
        return ResultUtils.success(voPage);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除标签")
    public BaseResponse<Boolean> deleteTag(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签ID不能为空");
        }

        Tags tag = tagsService.getById(request.getId());
        if (tag == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = tagsService.removeById(request.getId());
        return ResultUtils.success(removed);
    }
}
