package com.yupi.springbootinit.service.impi;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Images;
import com.yupi.springbootinit.mapper.ImagesMapper;
import com.yupi.springbootinit.service.ImagesService;
import com.yupi.springbootinit.utils.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
* @author 黎旺
* @description 针对表【images(图床文件表)】的数据库操作Service实现
* @createDate 2026-07-13 09:33:45
*/
@Service
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images>
    implements ImagesService {

    @Resource
    private OssUtil ossUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Images uploadImage(MultipartFile file, String source, String remark) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "上传文件不能为空");
        }

        // 计算 MD5 用于去重
        String md5;
        try (InputStream is = file.getInputStream()) {
            md5 = DigestUtil.md5Hex(is);
        }

        // MD5 去重：已存在的图片直接返回
        Images exist = lambdaQuery().eq(Images::getMd5, md5).one();
        if (exist != null) {
            return exist;
        }

        // 上传到 OSS
        OssUtil.OssUploadResult uploadResult = ossUtil.upload(file, source != null ? source : "blog");

        // 获取图片宽高
        Integer width = null, height = null;
        try (InputStream is = file.getInputStream()) {
            BufferedImage img = ImageIO.read(is);
            if (img != null) {
                width = img.getWidth();
                height = img.getHeight();
            }
        } catch (Exception ignored) {
            // 非图片文件或读取失败，忽略
        }

        // 保存记录
        Images images = new Images();
        images.setOriginalName(file.getOriginalFilename());
        images.setUrl(uploadResult.getFileUrl());
        images.setStoragePath(uploadResult.getOssKey());
        images.setFileSize(uploadResult.getFileSize().intValue());
        images.setFileType(uploadResult.getMimeType());
        images.setWidth(width);
        images.setHeight(height);
        images.setMd5(md5);
        images.setSource(source != null ? source : "");
        images.setRemark(remark != null ? remark : "");
        save(images);

        return images;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteImage(Integer id) {
        Images images = getById(id);
        if (images == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 删除 OSS 文件
        String storagePath = images.getStoragePath();
        if (storagePath != null && !storagePath.isEmpty()) {
            ossUtil.delete(storagePath);
        }

        // 删除数据库记录
        removeById(id);
    }
}




