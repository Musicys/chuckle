package com.yupi.springbootinit.model.dto.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 标签添加请求
 */
@ApiModel(description = "标签添加请求")
public class TagAddRequest {

    @ApiModelProperty(value = "标签名称", required = true)
    private String name;

    @ApiModelProperty(value = "标签颜色，默认 #409EFF")
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
