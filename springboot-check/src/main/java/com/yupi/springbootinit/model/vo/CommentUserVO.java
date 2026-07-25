package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 用户端评论视图（树状层级）
 */
@ApiModel(description = "用户端评论视图（树状层级）")
public class CommentUserVO {

    @ApiModelProperty(value = "评论ID")
    private Integer id;

    @ApiModelProperty(value = "评论者昵称")
    private String nickname;

    @ApiModelProperty(value = "评论者邮箱")
    private String email;

    @ApiModelProperty(value = "评论者头像URL")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "被回复的评论ID（null=一级评论）")
    private Integer replyToCommentId;

    @ApiModelProperty(value = "评论时间")
    private Date createdAt;

    @ApiModelProperty(value = "子回复列表")
    private List<CommentUserVO> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<CommentUserVO> getChildren() {
        return children;
    }

    public void setChildren(List<CommentUserVO> children) {
        this.children = children;
    }
}
