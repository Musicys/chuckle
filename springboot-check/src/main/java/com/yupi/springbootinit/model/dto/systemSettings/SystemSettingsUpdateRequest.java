package com.yupi.springbootinit.model.dto.systemSettings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统设置更新请求")
public class SystemSettingsUpdateRequest {

    @ApiModelProperty(value = "设置ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "配置键")
    private String settingKey;

    @ApiModelProperty(value = "配置值")
    private String settingValue;

    @ApiModelProperty(value = "配置描述")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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