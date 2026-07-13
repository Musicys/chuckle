package com.yupi.springbootinit.model.dto.comment;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评论查询请求（分页）
 */
@ApiModel(description = "评论查询请求（分页）")
public class CommentQueryRequest extends PageRequest {

    @ApiModelProperty(value = "所属文章ID")
    private Integer articleId;

    @ApiModelProperty(value = "状态（0=待审核 1=已通过 2=垃圾评论）")
    private Integer status;

    @ApiModelProperty(value = "评论者昵称（模糊搜索）")
    private String nickname;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
