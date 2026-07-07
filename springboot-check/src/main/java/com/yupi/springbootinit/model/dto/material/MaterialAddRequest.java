package com.yupi.springbootinit.model.dto.material;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员新增资料请求
 */
@Data
@ApiModel(description = "管理员新增资料请求")
public class MaterialAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "省份名称", required = true, example = "广东")
    private String province;

    @ApiModelProperty(value = "资料标题", required = true, example = "2025年广东省投档分数线汇总")
    private String title;

    @ApiModelProperty(value = "文件存储路径", required = true)
    private String filePath;

    @ApiModelProperty(value = "文件大小（字节）", example = "2048000")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型", example = "zip")
    private String fileType;

    @ApiModelProperty(value = "状态（0=下架 1=上架）", example = "1")
    private Integer status;
}
