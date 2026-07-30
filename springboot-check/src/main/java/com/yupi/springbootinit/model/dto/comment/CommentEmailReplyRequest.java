package com.yupi.springbootinit.model.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 邮件回复评论请求
 */
@ApiModel(description = "邮件回复评论请求")
public class CommentEmailReplyRequest {

    @ApiModelProperty(value = "评论ID", required = true)
    private Integer commentId;

    @ApiModelProperty(value = "收件人邮箱", required = true)
    private String toEmail;

    @ApiModelProperty(value = "邮件主题", required = true)
    private String subject;

    @ApiModelProperty(value = "邮件内容", required = true)
    private String content;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
