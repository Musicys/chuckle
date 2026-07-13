package com.yupi.springbootinit.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论表（树状嵌套）
 * @TableName comments
 */
@TableName(value ="comments")
public class Comments implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属文章ID（0=留言板留言）
     */
    private Integer articleId;

    /**
     * 父评论ID（NULL=一级评论）
     */
    private Integer parentId;

    /**
     * 被回复的评论ID
     */
    private Integer replyToCommentId;

    /**
     * 评论者昵称
     */
    private String nickname;

    /**
     * 评论者邮箱（用于回复通知）
     */
    private String email;

    /**
     * 评论者头像URL
     */
    private String avatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 状态（0=待审核 1=已通过 2=垃圾评论）
     */
    private Integer status;

    /**
     * 评论者IP
     */
    private String ipAddress;

    /**
     * 评论者UA
     */
    private String userAgent;

    /**
     * 评论时间
     */
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 所属文章ID（0=留言板留言）
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * 所属文章ID（0=留言板留言）
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * 父评论ID（NULL=一级评论）
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父评论ID（NULL=一级评论）
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 被回复的评论ID
     */
    public Integer getReplyToCommentId() {
        return replyToCommentId;
    }

    /**
     * 被回复的评论ID
     */
    public void setReplyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }

    /**
     * 评论者昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 评论者昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 评论者邮箱（用于回复通知）
     */
    public String getEmail() {
        return email;
    }

    /**
     * 评论者邮箱（用于回复通知）
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 评论者头像URL
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 评论者头像URL
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 状态（0=待审核 1=已通过 2=垃圾评论）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（0=待审核 1=已通过 2=垃圾评论）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 评论者IP
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 评论者IP
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 评论者UA
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 评论者UA
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 评论时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 评论时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Comments other = (Comments) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getReplyToCommentId() == null ? other.getReplyToCommentId() == null : this.getReplyToCommentId().equals(other.getReplyToCommentId()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getUserAgent() == null ? other.getUserAgent() == null : this.getUserAgent().equals(other.getUserAgent()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getReplyToCommentId() == null) ? 0 : getReplyToCommentId().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getUserAgent() == null) ? 0 : getUserAgent().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", articleId=").append(articleId);
        sb.append(", parentId=").append(parentId);
        sb.append(", replyToCommentId=").append(replyToCommentId);
        sb.append(", nickname=").append(nickname);
        sb.append(", email=").append(email);
        sb.append(", avatar=").append(avatar);
        sb.append(", content=").append(content);
        sb.append(", status=").append(status);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
