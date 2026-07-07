package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 兑换记录视图（订单）
 */
@Data
@ApiModel(description = "兑换记录视图")
public class RedeemLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "卡密ID")
    private Long cardKeyId;

    @ApiModelProperty(value = "卡密字符串")
    private String cardNo;

    @ApiModelProperty(value = "卡密类型")
    private Integer cardType;

    @ApiModelProperty(value = "卡密类型名称")
    private String cardTypeName;

    @ApiModelProperty(value = "获得的解锁次数")
    private Integer gainedQuota;

    @ApiModelProperty(value = "是否全通兑换（0=否 1=是）")
    private Integer isFullAccess;

    @ApiModelProperty(value = "是否全通兑换文本")
    private String isFullAccessText;

    @ApiModelProperty(value = "兑换时IP")
    private String ipAddress;

    @ApiModelProperty(value = "兑换时间")
    private Date createdAt;
}
