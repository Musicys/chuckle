package com.yupi.springbootinit.model.dto.portfolio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "作品更新请求")
public class PortfolioUpdateRequest {

    @ApiModelProperty(value = "作品ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "作品名称")
    private String title;

    @ApiModelProperty(value = "作品描述")
    private String description;

    @ApiModelProperty(value = "分类标签")
    private String category;

    @ApiModelProperty(value = "封面图片URL")
    private String cover;

    @ApiModelProperty(value = "项目链接/演示地址")
    private String projectUrl;

    @ApiModelProperty(value = "排序（越小越靠前）")
    private Integer sortOrder;

    @ApiModelProperty(value = "状态（0=隐藏 1=展示）")
    private Integer status;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }
    public String getProjectUrl() { return projectUrl; }
    public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
