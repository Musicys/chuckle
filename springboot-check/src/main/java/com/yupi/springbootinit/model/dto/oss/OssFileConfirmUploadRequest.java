package com.yupi.springbootinit.model.dto.oss;

import lombok.Data;

/**
 * OSS 直传确认请求
 */
@Data
public class OssFileConfirmUploadRequest {

    private String fileName;

    private String ossKey;

    private Long fileSize;

    private String mimeType;

    private String category;
}
