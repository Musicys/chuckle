package com.yupi.springbootinit.model.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评论审核请求
 */
@ApiModel(description = "评论审核请求")
public class CommentReviewRequest {

    @ApiModelProperty(value = "评论ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "审核状态（1=已通过 2=垃圾评论）", required = true)
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
