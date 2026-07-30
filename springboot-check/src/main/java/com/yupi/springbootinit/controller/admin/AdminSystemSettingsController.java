package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.SystemSettings;
import com.yupi.springbootinit.model.dto.systemSettings.SystemSettingsAddRequest;
import com.yupi.springbootinit.model.dto.systemSettings.SystemSettingsQueryRequest;
import com.yupi.springbootinit.model.dto.systemSettings.SystemSettingsUpdateRequest;
import com.yupi.springbootinit.service.SystemSettingsService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/system-settings")
@Api(tags = "管理端-系统设置")
public class AdminSystemSettingsController {

    @Resource
    private SystemSettingsService systemSettingsService;

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
    @ApiOperation(value = "新增系统设置")
    public BaseResponse<Integer> addSystemSettings(@RequestBody SystemSettingsAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getSettingKey())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "配置键不能为空");
        }

        SystemSettings entity = new SystemSettings();
        entity.setSettingKey(request.getSettingKey());
        entity.setSettingValue(request.getSettingValue() != null ? request.getSettingValue() : "");
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        systemSettingsService.save(entity);

        return ResultUtils.success(entity.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新系统设置")
    public BaseResponse<Boolean> updateSystemSettings(@RequestBody SystemSettingsUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "设置ID不能为空");
        }

        SystemSettings entity = systemSettingsService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (StringUtils.hasText(request.getSettingKey())) entity.setSettingKey(request.getSettingKey());
        if (request.getSettingValue() != null) entity.setSettingValue(request.getSettingValue());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());

        boolean updated = systemSettingsService.updateById(entity);
        return ResultUtils.success(updated);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询系统设置列表（支持配置键模糊搜索）")
    public BaseResponse<Page<SystemSettings>> listSystemSettings(@RequestBody SystemSettingsQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<SystemSettings> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getSettingKey())) {
            wrapper.like(SystemSettings::getSettingKey, request.getSettingKey());
        }
        wrapper.orderByDesc(SystemSettings::getId);

        Page<SystemSettings> page = systemSettingsService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        return ResultUtils.success(page);
    }

    @GetMapping("/get")
    @ApiOperation(value = "按ID获取单个系统设置详情")
    public BaseResponse<SystemSettings> getSystemSettingsById(@RequestParam Integer id, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "设置ID不能为空");
        }

        SystemSettings entity = systemSettingsService.getById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(entity);
    }

    @GetMapping("/get-by-key")
    @ApiOperation(value = "按配置键获取系统设置")
    public BaseResponse<SystemSettings> getSystemSettingsByKey(@RequestParam String settingKey, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(settingKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "配置键不能为空");
        }

        SystemSettings entity = systemSettingsService.lambdaQuery()
                .eq(SystemSettings::getSettingKey, settingKey)
                .one();
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(entity);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除系统设置")
    public BaseResponse<Boolean> deleteSystemSettings(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "设置ID不能为空");
        }

        SystemSettings entity = systemSettingsService.getById(request.getId());
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean removed = systemSettingsService.removeById(request.getId());
        return ResultUtils.success(removed);
    }

    @GetMapping("/all")
    @ApiOperation(value = "获取所有系统设置")
    public BaseResponse<List<SystemSettings>> getAllSystemSettings(HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        List<SystemSettings> list = systemSettingsService.lambdaQuery()
                .orderByDesc(SystemSettings::getId)
                .list();
        return ResultUtils.success(list);
    }
}