package com.yupi.springbootinit.model.dto.blogger;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 博主信息查询请求（分页）
 */
@ApiModel(description = "博主信息查询请求（分页）")
public class BloggerQueryRequest extends PageRequest {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "昵称（模糊搜索）")
    private String nickname;

    @ApiModelProperty(value = "博客标题（模糊搜索）")
    private String blogTitle;

    @ApiModelProperty(value = "是否启用（0=不使用 1=使用）")
    private Integer isUsed;

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

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
