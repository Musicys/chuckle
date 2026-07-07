package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 卡密兑换结果视图
 */
@Data
@ApiModel(description = "卡密兑换结果视图")
public class RedeemResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密类型
     */
    @ApiModelProperty(value = "卡密类型（1=单次 2=体验 3=标准 4=全通）")
    private Integer cardType;

    /**
     * 卡密类型名称
     */
    @ApiModelProperty(value = "卡密类型名称")
    private String cardTypeName;

    /**
     * 本次获得的解锁次数（全通卡密时为 0）
     */
    @ApiModelProperty(value = "本次获得的解锁次数")
    private Integer gainedQuota;

    /**
     * 是否为全通兑换
     */
    @ApiModelProperty(value = "是否为全通兑换")
    private Boolean isFullAccess;

    /**
     * 兑换后剩余解锁次数
     */
    @ApiModelProperty(value = "当前剩余解锁次数")
    private Integer currentQuota;

    /**
     * 兑换时间
     */
    @ApiModelProperty(value = "兑换时间")
    private Date redeemedAt;
}
