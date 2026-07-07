package com.yupi.springbootinit.model.dto.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户兑换卡密请求体
 */
@Data
@ApiModel(description = "用户兑换卡密请求")
public class CardRedeemRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密字符串
     */
    @ApiModelProperty(value = "卡密字符串", required = true, example = "A1B2-C3D4-E5F6-G7H8")
    private String cardNo;
}
