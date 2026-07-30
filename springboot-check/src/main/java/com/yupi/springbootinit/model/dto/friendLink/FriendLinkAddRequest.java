package com.yupi.springbootinit.model.dto.friendLink;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "友链添加请求")
public class FriendLinkAddRequest {

    @ApiModelProperty(value = "友站名称", required = true)
    private String name;

    @ApiModelProperty(value = "友站URL", required = true)
    private String url;

    @ApiModelProperty(value = "友站头像URL")
    private String avatar;

    @ApiModelProperty(value = "站点描述")
    private String description;

    @ApiModelProperty(value = "联系邮箱")
    private String email;

    @ApiModelProperty(value = "排序（越小越靠前）")
    private Integer sortOrder;

    @ApiModelProperty(value = "状态（0=隐藏 1=展示）")
    private Integer status;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
