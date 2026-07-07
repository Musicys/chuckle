package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息视图（脱敏，不含 Token）
 */
@Data
@ApiModel(description = "用户信息视图")
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
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

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;
}
