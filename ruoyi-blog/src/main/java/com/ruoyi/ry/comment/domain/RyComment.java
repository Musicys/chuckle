package com.ruoyi.ry.comment.domain;

import com.ruoyi.ry.message.domain.RyMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 评论对象 ry_comment
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public class RyComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 父评论id */
    @Excel(name = "父评论id")
    private Long parentId;

    /** 主评论id(第一级评论) */
    @Excel(name = "主评论id(第一级评论)")
    private Long mainId;

    /** 点赞数量 */
    @Excel(name = "点赞数量")
    private Long likeNum;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 评论类型：对人评论，对项目评论，对资源评论 */
    @Excel(name = "评论类型：对人评论，对项目评论，对资源评论")
    private String type;

    /** 被评论者id，可以是人、项目、资源 */
    @Excel(name = "被评论者id，可以是人、项目、资源")
    private Long blogId;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 评论者id */
    @Excel(name = "评论者id")
    private Long userId;

    //子评论者
    private List<RyComment> childComments;

    /** 头像 */
    private String avatar;

    // /**文章名*/
    // private String blogTitle;
    //
    /** 父评论者 */
    private String parentCreateBy;

    //博客标题
    private String blogTitle;

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<RyComment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<RyComment> childComments) {
        this.childComments = childComments;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getParentCreateBy() {
        return parentCreateBy;
    }

    public void setParentCreateBy(String parentCreateBy) {
        this.parentCreateBy = parentCreateBy;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setMainId(Long mainId)
    {
        this.mainId = mainId;
    }

    public Long getMainId()
    {
        return mainId;
    }
    public void setLikeNum(Long likeNum)
    {
        this.likeNum = likeNum;
    }

    public Long getLikeNum()
    {
        return likeNum;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setBlogId(Long blogId)
    {
        this.blogId = blogId;
    }

    public Long getBlogId()
    {
        return blogId;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("mainId", getMainId())
            .append("likeNum", getLikeNum())
            .append("content", getContent())
            .append("type", getType())
            .append("blogId", getBlogId())
            .append("delFlag", getDelFlag())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
