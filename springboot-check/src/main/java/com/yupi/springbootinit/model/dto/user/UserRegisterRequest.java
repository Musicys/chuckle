package com.yupi.springbootinit.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户注册请求体
 */
@Data
@ApiModel(description = "用户注册请求")
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名（4-20位）
     */
    @ApiModelProperty(value = "用户名（4-20位）", required = true, example = "zhangsan")
    private String username;

    /**
     * 密码（6-32位）
     */
    @ApiModelProperty(value = "密码（6-32位）", required = true, example = "abc123")
    private String password;

    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码（与密码一致）", required = true, example = "abc123")
    private String checkPassword;

    /**
     * 手机号（与邮箱至少填一个）
     */
    @ApiModelProperty(value = "手机号（与邮箱至少填一个）", example = "13800138000")
    private String phone;

    /**
     * 邮箱（与手机号至少填一个）
     */
    @ApiModelProperty(value = "邮箱（与手机号至少填一个）", example = "zhangsan@example.com")
    private String email;
}
