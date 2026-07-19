package com.yupi.springbootinit.model.dto.article;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户端文章查询请求（分页）
 */
@ApiModel(description = "用户端文章查询请求（分页）")
public class ArticleUserQueryRequest extends PageRequest {

    @ApiModelProperty(value = "搜索关键词（模糊匹配标题+正文）")
    private String keyword;

    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "标签ID")
    private Integer tagId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
