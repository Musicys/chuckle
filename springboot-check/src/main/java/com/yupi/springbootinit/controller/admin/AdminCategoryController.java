package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Categories;
import com.yupi.springbootinit.model.dto.category.CategoryAddRequest;
import com.yupi.springbootinit.model.dto.category.CategoryQueryRequest;
import com.yupi.springbootinit.model.dto.category.CategoryUpdateRequest;
import com.yupi.springbootinit.service.CategoriesService;
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
@RequestMapping("/admin/category")
@Api(tags = "管理端-分类管理")
public class AdminCategoryController {

    @Resource
    private CategoriesService categoriesService;

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
    @ApiOperation(value = "新增分类")
    public BaseResponse<Integer> addCategory(@RequestBody CategoryAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类名称不能为空");
        }

        // 检查分类名是否已存在
        long count = categoriesService.lambdaQuery().eq(Categories::getName, request.getName()).count();
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类名称已存在");
        }

        Categories category = new Categories();
        category.setName(request.getName());
        category.setDescription(request.getDescription() != null ? request.getDescription() : "");
        category.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        categoriesService.save(category);

        return ResultUtils.success(category.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新分类")
    public BaseResponse<Boolean> updateCategory(@RequestBody CategoryUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类ID不能为空");
        }

        Categories category = categoriesService.getById(request.getId());
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 如果修改了名称，检查是否与其他分类冲突
        if (StringUtils.hasText(request.getName()) && !request.getName().equals(category.getName())) {
            long count = categoriesService.lambdaQuery().eq(Categories::getName, request.getName()).count();
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类名称已存在");
            }
            category.setName(request.getName());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }

        boolean updated = categoriesService.updateById(category);
        return ResultUtils.success(updated);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询分类列表（管理端）")
    public BaseResponse<Page<Categories>> listCategories(@RequestBody CategoryQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<Categories> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getName())) {
            wrapper.like(Categories::getName, request.getName());
        }
        wrapper.orderByAsc(Categories::getSortOrder)
               .orderByDesc(Categories::getCreatedAt);

        Page<Categories> page = categoriesService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        return ResultUtils.success(page);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除分类")
    public BaseResponse<Boolean> deleteCategory(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类ID不能为空");
        }

        Categories category = categoriesService.getById(request.getId());
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = categoriesService.removeById(request.getId());
        return ResultUtils.success(removed);
    }
}
