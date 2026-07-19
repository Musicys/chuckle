package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.domain.Images;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
* @author 黎旺
* @description 针对表【images(图床文件表)】的数据库操作Service
* @createDate 2026-07-13 09:33:45
*/
public interface ImagesService extends IService<Images> {

    /**
     * 上传图片到图床
     *
     * @param file   上传的图片文件
     * @param source 图片来源（如 article/avatar/etc）
     * @param remark 备注
     * @return 图片记录
     */
    Images uploadImage(MultipartFile file, String source, String remark) throws IOException;

    /**
     * 删除图片（同时删除 OSS 文件）
     *
     * @param id 图片主键
     */
    void deleteImage(Integer id);
}
