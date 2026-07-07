package com.yupi.springbootinit.model.dto.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OSS 文件更新请求
 */
@Data
@ApiModel(description = "OSS文件更新请求")
public class OssFileUpdateRequest {

    @ApiModelProperty(value = "文件ID", required = true)
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件分类")
    private String category;
}
