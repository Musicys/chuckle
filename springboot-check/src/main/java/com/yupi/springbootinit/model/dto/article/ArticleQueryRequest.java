package com.yupi.springbootinit.model.dto.article;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文章查询请求（分页）
 */
@ApiModel(description = "文章查询请求（分页）")
public class ArticleQueryRequest extends PageRequest {

    @ApiModelProperty(value = "文章ID")
    private Integer id;

    @ApiModelProperty(value = "文章标题（模糊搜索）")
    private String title;

    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "状态（0=草稿 1=已发布 2=下架）")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除（0=未删 1=已删）")
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
