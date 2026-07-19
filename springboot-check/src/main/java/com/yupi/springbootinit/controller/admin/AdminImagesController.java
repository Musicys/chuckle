package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Images;
import com.yupi.springbootinit.model.dto.images.ImagesQueryRequest;
import com.yupi.springbootinit.model.dto.images.ImagesUpdateRequest;
import com.yupi.springbootinit.model.vo.ImagesVO;
import com.yupi.springbootinit.service.ImagesService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/image")
@Api(tags = "管理端-图床管理")
public class AdminImagesController {

    @Resource
    private ImagesService imagesService;

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

    @PostMapping("/upload")
    @ApiOperation(value = "上传图片")
    public BaseResponse<ImagesVO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "source", defaultValue = "blog") String source,
            @RequestParam(value = "remark", required = false) String remark,
            HttpServletRequest httpRequest) throws IOException {
        checkAdmin(httpRequest);

        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "上传文件不能为空");
        }

        Images images = imagesService.uploadImage(file, source, remark);

        ImagesVO vo = new ImagesVO();
        BeanUtils.copyProperties(images, vo);
        return ResultUtils.success(vo);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新图片信息")
    public BaseResponse<Boolean> updateImage(@RequestBody ImagesUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        Integer id = request.getId();
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图片ID不能为空");
        }

        Images images = imagesService.getById(id);
        if (images == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        images.setRemark(request.getRemark() != null ? request.getRemark() : images.getRemark());
        boolean updated = imagesService.updateById(images);
        return ResultUtils.success(updated);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询图片列表（管理端）")
    public BaseResponse<Page<ImagesVO>> listImages(@RequestBody ImagesQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<Images> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(request.getOriginalName())) {
            wrapper.like(Images::getOriginalName, request.getOriginalName());
        }
        if (StringUtils.hasText(request.getFileType())) {
            wrapper.eq(Images::getFileType, request.getFileType());
        }
        if (StringUtils.hasText(request.getSource())) {
            wrapper.eq(Images::getSource, request.getSource());
        }
        wrapper.orderByDesc(Images::getCreatedAt);

        Page<Images> page = imagesService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        Page<ImagesVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<ImagesVO> records = page.getRecords().stream().map(image -> {
            ImagesVO vo = new ImagesVO();
            BeanUtils.copyProperties(image, vo);
            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(records);
        return ResultUtils.success(voPage);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取图片详情")
    public BaseResponse<Images> getImage(@RequestParam Integer id, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        Images images = imagesService.getById(id);
        if (images == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(images);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除图片（同时删除 OSS 文件）")
    public BaseResponse<Boolean> deleteImage(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图片ID不能为空");
        }

        imagesService.deleteImage(request.getId().intValue());
        return ResultUtils.success(true);
    }
}
