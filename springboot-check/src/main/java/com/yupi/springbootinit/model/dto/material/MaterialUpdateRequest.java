package com.yupi.springbootinit.model.dto.material;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员编辑资料请求
 */
@Data
@ApiModel(description = "管理员编辑资料请求")
public class MaterialUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资料ID", required = true)
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

    @ApiModelProperty(value = "状态（0=下架 1=上架）")
    private Integer status;
}
