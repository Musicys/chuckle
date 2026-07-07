package com.yupi.springbootinit.model.dto.user;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员查询用户请求体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "管理员查询用户请求")
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名（模糊搜索）
     */
    @ApiModelProperty(value = "用户名（模糊搜索）")
    private String username;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 角色：0=普通用户 1=管理员
     */
    @ApiModelProperty(value = "角色（0=普通用户 1=管理员）")
    private Integer role;

    /**
     * 状态：0=禁用 1=正常
     */
    @ApiModelProperty(value = "状态（0=禁用 1=正常）")
    private Integer status;
}
