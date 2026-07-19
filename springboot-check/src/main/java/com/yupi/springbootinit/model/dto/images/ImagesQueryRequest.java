package com.yupi.springbootinit.model.dto.images;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 图床文件查询请求（分页）
 */
@ApiModel(description = "图床文件查询请求（分页）")
public class ImagesQueryRequest extends PageRequest {

    @ApiModelProperty(value = "原始文件名（模糊搜索）")
    private String originalName;

    @ApiModelProperty(value = "文件类型（MIME）")
    private String fileType;

    @ApiModelProperty(value = "来源")
    private String source;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
