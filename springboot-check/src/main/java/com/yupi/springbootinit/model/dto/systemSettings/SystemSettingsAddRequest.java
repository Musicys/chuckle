package com.yupi.springbootinit.model.dto.systemSettings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统设置添加请求")
public class SystemSettingsAddRequest {

    @ApiModelProperty(value = "配置键", required = true)
    private String settingKey;

    @ApiModelProperty(value = "配置值")
    private String settingValue;

    @ApiModelProperty(value = "配置描述")
    private String description;

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}