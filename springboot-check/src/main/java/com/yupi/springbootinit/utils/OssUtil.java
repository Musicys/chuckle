package com.yupi.springbootinit.utils;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.UploadFileRequest;
import com.yupi.springbootinit.config.OssConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 */
@Component
public class OssUtil {

    /**
     * 大文件阈值：超过此大小（10MB）使用分片上传
     */
    private static final long LARGE_FILE_THRESHOLD = 10L * 1024 * 1024;

    /**
     * 分片大小：5MB
     */
    private static final long PART_SIZE = 5L * 1024 * 1024;

    /**
     * 分片上传并发数
     */
    private static final int TASK_NUM = 5;

    @Resource
    private OSS ossClient;

    @Resource
    private OssConfig ossConfig;

    /**
     * 上传文件到 OSS
     *
     * @param file       上传的文件
     * @param category   文件分类
     * @return OssUploadResult 上传结果（ossKey, fileUrl, fileSize, mimeType, fileExt）
     */
    public OssUploadResult upload(MultipartFile file, String category) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileExt = "";
        if (StrUtil.isNotBlank(originalFilename) && originalFilename.contains(".")) {
            fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 按分类和日期组织路径：category/yyyy-MM-dd/original_filename
        String datePath = cn.hutool.core.date.DateUtil.format(new Date(), "yyyy-MM-dd");
        String safeName = originalFilename != null ? originalFilename.replaceAll("[/\\\\:*?\"<>|]", "_") : UUID.randomUUID().toString().replace("-", "") + fileExt;
        String ossKey = category + "/" + datePath + "/" + safeName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        String encodedName = java.net.URLEncoder.encode(originalFilename != null ? originalFilename : safeName, "UTF-8").replace("+", "%20");
        metadata.setContentDisposition("attachment; filename*=UTF-8''" + encodedName);

        long fileSize = file.getSize();
        if (fileSize >= LARGE_FILE_THRESHOLD) {
            // 大文件使用分片上传（支持断点续传）
            uploadByMultipart(file, ossKey, metadata);
        } else {
            // 小文件使用普通上传
            try (InputStream inputStream = file.getInputStream()) {
                PutObjectRequest putRequest = new PutObjectRequest(ossConfig.getBucketname(), ossKey, inputStream, metadata);
                ossClient.putObject(putRequest);
            }
        }

        // 生成访问 URL（有效期 100 年，相当于永久）
        Date expiration = new Date(System.currentTimeMillis() + 100L * 365 * 24 * 60 * 60 * 1000);
        URL url = ossClient.generatePresignedUrl(ossConfig.getBucketname(), ossKey, expiration);
        String fileUrl = url.toString();
        // 截取到 ? 之前，去掉签名参数，生成公开访问 URL
        if (fileUrl.contains("?")) {
            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
        }

        OssUploadResult result = new OssUploadResult();
        result.setOssKey(ossKey);
        result.setFileUrl(fileUrl);
        result.setFileSize(fileSize);
        result.setMimeType(file.getContentType());
        result.setFileExt(fileExt);
        return result;
    }

    /**
     * 使用阿里云 OSS 分片上传（支持断点续传），适合大文件
     */
    private void uploadByMultipart(MultipartFile file, String ossKey, ObjectMetadata metadata) throws IOException {
        File tempFile = File.createTempFile("oss_upload_", ".tmp");
        try {
            file.transferTo(tempFile);
            UploadFileRequest request = new UploadFileRequest(ossConfig.getBucketname(), ossKey);
            request.setUploadFile(tempFile.getAbsolutePath());
            request.setPartSize(PART_SIZE);
            request.setTaskNum(TASK_NUM);
            request.setEnableCheckpoint(true);
            request.setObjectMetadata(metadata);
            try {
                ossClient.uploadFile(request);
            } catch (Throwable e) {
                throw new IOException("OSS分片上传失败", e);
            }
        } finally {
            tempFile.delete();
        }
    }

    /**
     * 从 OSS 删除文件
     *
     * @param ossKey OSS 对象 Key
     */
    public void delete(String ossKey) {
        ossClient.deleteObject(ossConfig.getBucketname(), ossKey);
    }

    /**
     * OSS 上传结果
     */
    public static class OssUploadResult {
        private String ossKey;
        private String fileUrl;
        private Long fileSize;
        private String mimeType;
        private String fileExt;

        public String getOssKey() { return ossKey; }
        public void setOssKey(String ossKey) { this.ossKey = ossKey; }
        public String getFileUrl() { return fileUrl; }
        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
        public Long getFileSize() { return fileSize; }
        public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
        public String getMimeType() { return mimeType; }
        public void setMimeType(String mimeType) { this.mimeType = mimeType; }
        public String getFileExt() { return fileExt; }
        public void setFileExt(String fileExt) { this.fileExt = fileExt; }
    }
}
