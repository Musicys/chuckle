package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 管理端评论视图
 */
@ApiModel(description = "管理端评论视图")
public class CommentAdminVO {

    @ApiModelProperty(value = "评论ID")
    private Integer id;

    @ApiModelProperty(value = "所属文章ID")
    private Integer articleId;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "父评论ID")
    private Integer parentId;

    @ApiModelProperty(value = "被回复的评论ID")
    private Integer replyToCommentId;

    @ApiModelProperty(value = "评论者昵称")
    private String nickname;

    @ApiModelProperty(value = "评论者邮箱")
    private String email;

    @ApiModelProperty(value = "评论者头像URL")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "状态（0=待审核 1=已通过 2=垃圾评论）")
    private Integer status;

    @ApiModelProperty(value = "评论者IP")
    private String ipAddress;

    @ApiModelProperty(value = "评论时间")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getReplyToCommentId() {
        return replyToCommentId;
    }

    public void setReplyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
