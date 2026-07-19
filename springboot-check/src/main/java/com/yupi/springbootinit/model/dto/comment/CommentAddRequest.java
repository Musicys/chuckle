package com.yupi.springbootinit.model.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户提交评论请求
 */
@ApiModel(description = "用户提交评论请求")
public class CommentAddRequest {

    @ApiModelProperty(value = "所属文章ID", required = true)
    private Integer articleId;

    @ApiModelProperty(value = "评论者昵称", required = true)
    private String nickname;

    @ApiModelProperty(value = "评论者邮箱（用于回复通知）")
    private String email;

    @ApiModelProperty(value = "评论者头像URL")
    private String avatar;

    @ApiModelProperty(value = "评论内容", required = true)
    private String content;

    @ApiModelProperty(value = "被回复的评论ID（不传则为一级评论）")
    private Integer replyToCommentId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyToCommentId() {
        return replyToCommentId;
    }

    public void setReplyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }
}
