package com.yupi.springbootinit.model.dto.portfolio;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "作品查询请求（分页）")
public class PortfolioQueryRequest extends PageRequest {

    @ApiModelProperty(value = "作品名称（模糊搜索）")
    private String title;

    @ApiModelProperty(value = "分类标签")
    private String category;

    @ApiModelProperty(value = "状态（0=隐藏 1=展示）")
    private Integer status;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
