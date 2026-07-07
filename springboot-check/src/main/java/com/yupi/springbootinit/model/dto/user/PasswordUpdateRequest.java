package com.yupi.springbootinit.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "修改密码请求")
public class PasswordUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @ApiModelProperty(value = "新密码（6-32位）", required = true)
    private String newPassword;

    @ApiModelProperty(value = "确认新密码", required = true)
    private String checkPassword;
}
