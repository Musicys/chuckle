package com.yupi.springbootinit.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 管理员更新用户请求体
 */
@Data
@ApiModel(description = "管理员更新用户请求")
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID（必填）
     */
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名（4-20位）")
    private String username;

    /**
     * 密码（留空则不修改）
     */
    @ApiModelProperty(value = "新密码（留空则不修改，6-32位）")
    private String password;

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
     * 剩余解锁次数
     */
    @ApiModelProperty(value = "剩余解锁次数")
    private Integer unlockQuota;

    /**
     * 是否全通用户：0=否 1=是
     */
    @ApiModelProperty(value = "是否全通用户（0=否 1=是）")
    private Integer isFullAccess;

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
