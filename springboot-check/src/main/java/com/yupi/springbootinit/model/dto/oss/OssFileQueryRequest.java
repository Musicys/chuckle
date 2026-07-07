package com.yupi.springbootinit.model.dto.oss;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS 文件查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "OSS文件查询请求")
public class OssFileQueryRequest extends PageRequest {

    @ApiModelProperty(value = "文件分类筛选：image/document/video/archive/other")
    private String category;

    @ApiModelProperty(value = "文件名关键词搜索")
    private String keyword;

    @ApiModelProperty(value = "开始日期 yyyy-MM-dd")
    private String startDate;

    @ApiModelProperty(value = "结束日期 yyyy-MM-dd")
    private String endDate;
}
