package com.ruoyi.ry.file.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * blog文件对象 ry_blog_file
 * 
 * @author ruoyi
 * @date 2025-02-26
 */
public class RyBlogFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    private Long fileId;

    /** blogID */
    private Long blogId;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setBlogId(Long blogId) 
    {
        this.blogId = blogId;
    }

    public Long getBlogId() 
    {
        return blogId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("blogId", getBlogId())
            .toString();
    }
}
