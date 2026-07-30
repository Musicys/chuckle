package com.yupi.springbootinit.model.dto.friendLink;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "友链查询请求（分页）")
public class FriendLinkQueryRequest extends PageRequest {

    @ApiModelProperty(value = "友站名称（模糊搜索）")
    private String name;

    @ApiModelProperty(value = "状态（0=隐藏 1=展示）")
    private Integer status;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
