package com.yupi.springbootinit.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云 OSS 配置
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class OssConfig {

    private String endpoint;

    private String keyid;

    private String keysecret;

    private String bucketname;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, keyid, keysecret);
    }
}
