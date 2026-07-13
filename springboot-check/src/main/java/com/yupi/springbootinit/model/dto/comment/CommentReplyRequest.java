package com.yupi.springbootinit.model.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员回复评论请求
 */
@ApiModel(description = "管理员回复评论请求")
public class CommentReplyRequest {

    @ApiModelProperty(value = "所属文章ID", required = true)
    private Integer articleId;

    @ApiModelProperty(value = "被回复的评论ID", required = true)
    private Integer replyToCommentId;

    @ApiModelProperty(value = "回复内容", required = true)
    private String content;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getReplyToCommentId() {
        return replyToCommentId;
    }

    public void setReplyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
