package com.yupi.springbootinit.model.dto.redeem;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 兑换记录查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "兑换记录查询请求")
public class RedeemLogQueryRequest extends PageRequest {

    /**
     * 用户名（模糊搜索）
     */
    @ApiModelProperty(value = "用户名（模糊搜索）")
    private String username;

    /**
     * 卡密字符串（模糊搜索）
     */
    @ApiModelProperty(value = "卡密字符串（模糊搜索）")
    private String cardNo;

    /**
     * 卡密类型
     */
    @ApiModelProperty(value = "卡密类型（1=单次 2=体验 3=标准 4=全通 5=无限制）")
    private Integer cardType;

    /**
     * 是否全通兑换：0=否 1=是
     */
    @ApiModelProperty(value = "是否全通兑换（0=否 1=是）")
    private Integer isFullAccess;

    /**
     * 开始日期 yyyy-MM-dd
     */
    @ApiModelProperty(value = "开始日期（yyyy-MM-dd）")
    private String startDate;

    /**
     * 结束日期 yyyy-MM-dd
     */
    @ApiModelProperty(value = "结束日期（yyyy-MM-dd）")
    private String endDate;
}
