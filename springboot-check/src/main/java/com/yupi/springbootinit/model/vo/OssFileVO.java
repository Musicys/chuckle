package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * OSS 文件视图
 */
@Data
@ApiModel(description = "OSS文件视图")
public class OssFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    private Long id;

    @ApiModelProperty(value = "原始文件名")
    private String fileName;

    @ApiModelProperty(value = "OSS存储路径")
    private String ossKey;

    @ApiModelProperty(value = "文件访问URL")
    private String fileUrl;

    @ApiModelProperty(value = "文件大小（字节）")
    private Long fileSize;

    @ApiModelProperty(value = "MIME类型")
    private String mimeType;

    @ApiModelProperty(value = "文件扩展名")
    private String fileExt;

    @ApiModelProperty(value = "文件分类")
    private String category;

    @ApiModelProperty(value = "上传者管理员ID")
    private Long uploadedBy;

    @ApiModelProperty(value = "上传者用户名")
    private String uploaderName;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;
}
