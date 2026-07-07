package com.yupi.springbootinit.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户登录请求体
 */
@Data
@ApiModel(description = "用户登录请求")
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号（用户名 / 手机号 / 邮箱）
     */
    @ApiModelProperty(value = "登录账号（用户名/手机号/邮箱）", required = true, example = "zhangsan")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true, example = "abc123")
    private String password;
}
