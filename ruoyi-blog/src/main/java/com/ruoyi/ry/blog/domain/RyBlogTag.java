package com.ruoyi.ry.blog.domain;

import java.util.Arrays;

/**
 * @Author hyb
 * @Date 2025/2/26 21:20
 * @Version 1.0
 */
public class RyBlogTag {
    private Long tagId;
    private Long blogId;
    private Long[] tagIds;

    @Override
    public String toString() {
        return "RyBlogTag{" +
                "tagId=" + tagId +
                ", blogId=" + blogId +
                ", tagIds=" + Arrays.toString(tagIds) +
                '}';
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }
}
