package com.yupi.springbootinit.model.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 刷新 Token 请求
 */
@ApiModel(description = "刷新 Token 请求")
public class AdminRefreshTokenRequest {

    @ApiModelProperty(value = "刷新令牌", required = true)
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
