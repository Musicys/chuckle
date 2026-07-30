package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 文章归档视图
 */
@ApiModel(description = "文章归档视图")
public class ArchiveVO {

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "该年文章数量")
    private Integer count;

    @ApiModelProperty(value = "该年文章列表（最多100条）")
    private List<ArchiveItemVO> articles;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ArchiveItemVO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArchiveItemVO> articles) {
        this.articles = articles;
    }
}
