package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员登录响应
 */
@ApiModel(description = "管理员登录响应")
public class AdminLoginVO {

    @ApiModelProperty(value = "JWT 访问令牌")
    private String token;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    public AdminLoginVO() {}

    public AdminLoginVO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
