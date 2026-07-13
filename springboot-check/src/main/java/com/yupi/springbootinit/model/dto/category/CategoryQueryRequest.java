package com.yupi.springbootinit.model.dto.category;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分类查询请求（分页）
 */
@ApiModel(description = "分类查询请求（分页）")
public class CategoryQueryRequest extends PageRequest {

    @ApiModelProperty(value = "分类名称（模糊搜索）")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
