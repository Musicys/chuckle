package com.ruoyi.common.utils;

import com.aliyun.oss.OSS;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Log4j2
public class OssService {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    /**
     * 上传文件到OSS
     */
    public String uploadFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";

            // 构建唯一文件名
            String fileName =format + file.getOriginalFilename();

            // 上传文件流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 返回文件访问链接
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        }catch (IOException e) {
            log.error("Failed to upload file", e);
            throw e;
        }
    }


}
