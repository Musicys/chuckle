package com.yupi.springbootinit.model.dto.systemSettings;

import com.yupi.springbootinit.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统设置查询请求（分页）")
public class SystemSettingsQueryRequest extends PageRequest {

    @ApiModelProperty(value = "配置键（模糊搜索）")
    private String settingKey;

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }
}