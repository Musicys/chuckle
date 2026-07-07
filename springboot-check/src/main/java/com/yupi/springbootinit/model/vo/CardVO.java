package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 卡密视图
 */
@Data
@ApiModel(description = "卡密视图")
public class CardVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密 ID
     */
    @ApiModelProperty(value = "卡密ID")
    private Long id;

    /**
     * 卡密字符串
     */
    @ApiModelProperty(value = "卡密字符串")
    private String cardNo;

    /**
     * 类型：1=单次 2=体验 3=标准 4=全通
     */
    @ApiModelProperty(value = "卡密类型（1=单次 2=体验 3=标准 4=全通）")
    private Integer type;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "卡密类型名称")
    private String typeName;

    /**
     * 状态：0=未激活 1=已激活 2=已使用 3=已过期
     */
    @ApiModelProperty(value = "卡密状态（0=未激活 1=已激活 2=已使用 3=已过期）")
    private Integer status;

    /**
     * 状态名称
     */
    @ApiModelProperty(value = "卡密状态名称")
    private String statusName;

    /**
     * 使用该卡密的用户 ID
     */
    @ApiModelProperty(value = "使用用户ID")
    private Long usedBy;

    /**
     * 绑定用户名
     */
    @ApiModelProperty(value = "绑定用户名")
    private String username;

    /**
     * 使用时间
     */
    @ApiModelProperty(value = "使用时间")
    private Date usedAt;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private Date expiredAt;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;
}
