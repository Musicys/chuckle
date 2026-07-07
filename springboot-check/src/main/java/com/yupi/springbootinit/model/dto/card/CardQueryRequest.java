package com.yupi.springbootinit.model.dto.card;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员查询卡密请求体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "管理员查询卡密请求")
public class CardQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密类型筛选
     */
    @ApiModelProperty(value = "卡密类型（1=单次 2=体验 3=标准 4=全通）")
    private Integer type;

    /**
     * 状态筛选：0=未激活 1=已激活 2=已使用 3=已过期
     */
    @ApiModelProperty(value = "卡密状态（0=未激活 1=已激活 2=已使用 3=已过期）")
    private Integer status;

    /**
     * 卡密字符串（模糊搜索）
     */
    @ApiModelProperty(value = "卡密字符串（模糊搜索）")
    private String cardNo;

    /**
     * 绑定用户ID 筛选
     */
    @ApiModelProperty(value = "绑定用户ID")
    private Long userId;
}
