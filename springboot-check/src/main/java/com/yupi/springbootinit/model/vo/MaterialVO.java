package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料视图（管理端使用，含全部字段）
 */
@Data
@ApiModel(description = "资料视图（管理端）")
public class MaterialVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资料ID")
    private Long id;

    @ApiModelProperty(value = "省份名称")
    private String province;

    @ApiModelProperty(value = "资料标题")
    private String title;

    @ApiModelProperty(value = "文件存储路径")
    private String filePath;

    @ApiModelProperty(value = "文件大小（字节）")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "累计下载次数")
    private Integer downloadCount;

    @ApiModelProperty(value = "累计解锁次数")
    private Integer unlockCount;

    @ApiModelProperty(value = "状态（0=下架 1=上架）")
    private Integer status;

    @ApiModelProperty(value = "上传者管理员ID")
    private Long uploadedBy;

    @ApiModelProperty(value = "上传者用户名")
    private String uploaderName;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;
}
