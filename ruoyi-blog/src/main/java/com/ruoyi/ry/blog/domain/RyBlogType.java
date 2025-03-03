package com.ruoyi.ry.blog.domain;

import java.util.Arrays;

/**
 * @Author hyb
 * @Date 2025/2/26 21:20
 * @Version 1.0
 */
public class RyBlogType {
    private Long typeId;
    private Long blogId;
    private Long[] typeIds;

    @Override
    public String toString() {
        return "RyBlogType{" +
                "typeId=" + typeId +
                ", blogId=" + blogId +
                ", typeIds=" + Arrays.toString(typeIds) +
                '}';
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long[] getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(Long[] typeIds) {
        this.typeIds = typeIds;
    }
}
