package com.ruoyi.ry.blog.domain;

import com.ruoyi.ry.tag.domain.RyTag;
import com.ruoyi.ry.type.domain.RyType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * 博客信息对象 ry_blog
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public class RyBlog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 文本编辑器类型 */
    @Excel(name = "文本编辑器类型")
    private String contentType;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** Markdown格式内容 */
    @Excel(name = "Markdown格式内容")
    private String contentMarkdown;

    /** 置顶（0否 1是） */
    @Excel(name = "置顶", readConverterExp = "0=否,1=是")
    private String Top;

    /** 阅读 */
    @Excel(name = "阅读")
    private Long views;

    /** 状态（0暂存 1发布） */
    @Excel(name = "状态", readConverterExp = "0=暂存,1=发布")
    private String status;

    /** 首页图片类型（0地址 1上传） */
    @Excel(name = "首页图片类型", readConverterExp = "0=地址,1=上传")
    private String blogPicType;

    /** 首页图片（ 1上传） */
    @Excel(name = "首页图片", readConverterExp = "1=上传")
    private String blogPic;

    /** 首页图片（ 0地址） */
    @Excel(name = "首页图片", readConverterExp = "0=地址")
    private String blogPicLink;

    /** 简介 */
    @Excel(name = "简介")
    private String blogDesc;

    /** 附件列表 */
    @Excel(name = "附件列表")
    private String blogFiles;

    /**
     * 分类
     */
    private Long[] typeIds;

    /**
     * 分类
     */
    private Long[] tagIds;

    /** 角色对象 */
    private List<RyTag> tags;
    /** 角色对象 */
    private List<RyType> types;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long[] getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(Long[] typeIds) {
        this.typeIds = typeIds;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

    public List<RyTag> getTags() {
        return tags;
    }

    public void setTags(List<RyTag> tags) {
        this.tags = tags;
    }

    public List<RyType> getTypes() {
        return types;
    }

    public void setTypes(List<RyType> types) {
        this.types = types;
    }



    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setContentMarkdown(String contentMarkdown)
    {
        this.contentMarkdown = contentMarkdown;
    }

    public String getContentMarkdown()
    {
        return contentMarkdown;
    }
    public void setTop(String Top)
    {
        this.Top = Top;
    }

    public String getTop()
    {
        return Top;
    }
    public void setViews(Long views)
    {
        this.views = views;
    }

    public Long getViews()
    {
        return views;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setBlogPicType(String blogPicType)
    {
        this.blogPicType = blogPicType;
    }

    public String getBlogPicType()
    {
        return blogPicType;
    }
    public void setBlogPic(String blogPic)
    {
        this.blogPic = blogPic;
    }

    public String getBlogPic()
    {
        return blogPic;
    }
    public void setBlogPicLink(String blogPicLink)
    {
        this.blogPicLink = blogPicLink;
    }

    public String getBlogPicLink()
    {
        return blogPicLink;
    }
    public void setBlogDesc(String blogDesc)
    {
        this.blogDesc = blogDesc;
    }

    public String getBlogDesc()
    {
        return blogDesc;
    }
    public void setBlogFiles(String blogFiles)
    {
        this.blogFiles = blogFiles;
    }

    public String getBlogFiles()
    {
        return blogFiles;
    }

    @Override
    public String toString() {
        return "RyBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content='" + content + '\'' +
                ", contentMarkdown='" + contentMarkdown + '\'' +
                ", Top='" + Top + '\'' +
                ", views=" + views +
                ", status='" + status + '\'' +
                ", blogPicType='" + blogPicType + '\'' +
                ", blogPic='" + blogPic + '\'' +
                ", blogPicLink='" + blogPicLink + '\'' +
                ", blogDesc='" + blogDesc + '\'' +
                ", blogFiles='" + blogFiles + '\'' +
                ", typeIds=" + Arrays.toString(typeIds) +
                ", tagIds=" + Arrays.toString(tagIds) +
                ", tags=" + tags +
                ", types=" + types +
                '}';
    }
}
