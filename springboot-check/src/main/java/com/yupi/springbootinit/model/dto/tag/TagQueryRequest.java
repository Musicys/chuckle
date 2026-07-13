package com.yupi.springbootinit.model.dto.tag;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 标签查询请求（分页）
 */
@ApiModel(description = "标签查询请求（分页）")
public class TagQueryRequest extends PageRequest {

    @ApiModelProperty(value = "标签名称（模糊搜索）")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
