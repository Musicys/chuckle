package com.yupi.springbootinit.model.dto.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 管理员生成卡密请求体
 */
@Data
@ApiModel(description = "管理员生成卡密请求")
public class CardGenerateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密类型：1=单次(1份) 2=体验(2份) 3=标准(5份) 4=全通
     */
    @ApiModelProperty(value = "卡密类型（1=单次 2=体验 3=标准 4=全通 5=无限制）", required = true, example = "3")
    private Integer type;

    /**
     * 生成数量（1-100）
     */
    @ApiModelProperty(value = "生成数量（1-100）", required = true, example = "10")
    private Integer count;

    /**
     * 有效期天数（默认 365 天）
     */
    @ApiModelProperty(value = "有效期天数（默认365天）", example = "180")
    private Integer expiredDays;
}
