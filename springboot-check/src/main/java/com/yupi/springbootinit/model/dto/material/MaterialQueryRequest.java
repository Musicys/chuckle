package com.yupi.springbootinit.model.dto.material;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资料查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "资料查询请求")
public class MaterialQueryRequest extends PageRequest {

    @ApiModelProperty(value = "省份筛选")
    private String province;

    @ApiModelProperty(value = "标题关键词搜索")
    private String keyword;

    @ApiModelProperty(value = "状态（0=下架 1=上架）")
    private Integer status;
}
