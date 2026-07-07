package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料视图（用户端使用，不含 filePath，含 isUnlocked）
 */
@Data
@ApiModel(description = "资料视图（用户端）")
public class UserMaterialVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资料ID")
    private Long id;

    @ApiModelProperty(value = "省份名称")
    private String province;

    @ApiModelProperty(value = "资料标题")
    private String title;

    @ApiModelProperty(value = "文件大小（字节）")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "累计下载次数")
    private Integer downloadCount;

    @ApiModelProperty(value = "当前用户是否已解锁")
    private Boolean isUnlocked;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;
}
