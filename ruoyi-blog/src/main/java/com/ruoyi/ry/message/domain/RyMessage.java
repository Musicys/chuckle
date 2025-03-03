package com.ruoyi.ry.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 留言对象 ry_message
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public class RyMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 父留言id */
    @Excel(name = "父留言id")
    private Long parentId;

    /** 主留言id(第一级留言) */
    @Excel(name = "主留言id(第一级留言)")
    private Long mainId;

    /** 点赞数量 */
    @Excel(name = "点赞数量")
    private Long likeNum;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 留言类型：对人评论，对项目评论，对资源评论（0代表留言 1代表回复） */
    @Excel(name = "留言类型：对人评论，对项目评论，对资源评论", readConverterExp = "0=代表留言,1=代表回复")
    private String type;

    /** 被留言者id，可以是人、项目、资源 */
    @Excel(name = "被留言者id，可以是人、项目、资源")
    private Long blogId;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;


    /** 留言者id */
    @Excel(name = "留言者id")
    private Long userId;

    //子留言
    private List<RyMessage> childMessages;

    /** 头像 */
    private String avatar;

    // /**文章名*/
    // private String blogTitle;
    //
    /** 父留言者 */
    private String parentCreateBy;

    public String getParentCreateBy() {
        return parentCreateBy;
    }

    public void setParentCreateBy(String parentCreateBy) {
        this.parentCreateBy = parentCreateBy;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<RyMessage> getChildMessages() {
        return childMessages;
    }

    public void setChildMessages(List<RyMessage> childMessages) {
        this.childMessages = childMessages;
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
