package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "文章搜索结果（含高亮）")
public class ArticleSearchVO {

    @ApiModelProperty(value = "文章ID")
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "高亮标题（含<em>标签）")
    private String titleHighlight;

    @ApiModelProperty(value = "文章简介")
    private String summary;

    @ApiModelProperty(value = "高亮简介（含<em>标签）")
    private String summaryHighlight;

    @ApiModelProperty(value = "封面URL")
    private String cover;

    @ApiModelProperty(value = "分类信息")
    private ArticleUserVO.CategoryVO category;

    @ApiModelProperty(value = "标签列表")
    private List<TagVO> tags;

    @ApiModelProperty(value = "字数")
    private Integer wordCount;

    @ApiModelProperty(value = "阅读量（PV）")
    private Integer readCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "是否最新（一周内发布）")
    private Boolean isNew;

    @ApiModelProperty(value = "发布时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

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

    public String getTitleHighlight() {
        return titleHighlight;
    }

    public void setTitleHighlight(String titleHighlight) {
        this.titleHighlight = titleHighlight;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummaryHighlight() {
        return summaryHighlight;
    }

    public void setSummaryHighlight(String summaryHighlight) {
        this.summaryHighlight = summaryHighlight;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ArticleUserVO.CategoryVO getCategory() {
        return category;
    }

    public void setCategory(ArticleUserVO.CategoryVO category) {
        this.category = category;
    }

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
