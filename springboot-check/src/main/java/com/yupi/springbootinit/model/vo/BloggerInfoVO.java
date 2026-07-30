package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "博主公开信息视图")
public class BloggerInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "博客标题")
    private String blogTitle;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "GitHub 链接")
    private String github;

    @ApiModelProperty(value = "Gitee 链接")
    private String gitee;

    @ApiModelProperty(value = "Bilibili 链接")
    private String bilibili;

    @ApiModelProperty(value = "出生年月")
    private Date birthDate;

    @ApiModelProperty(value = "性别（0=保密 1=男 2=女）")
    private Integer gender;

    @ApiModelProperty(value = "现职业")
    private String occupation;

    @ApiModelProperty(value = "个性标签（JSON数组）")
    private String tags;

    @ApiModelProperty(value = "爱好（JSON数组）")
    private String hobbies;

    @ApiModelProperty(value = "爱好游戏（JSON数组）")
    private String games;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "文字地址")
    private String address;

    @ApiModelProperty(value = "博客公告（Markdown格式）")
    private String announcement;

    @ApiModelProperty(value = "个人配置（JSON格式，如主题、布局、功能开关等）")
    private String config;

    @ApiModelProperty(value = "是否启用（0=不使用 1=使用）")
    private Integer isUsed;

    @ApiModelProperty(value = "博主主页统计列表")
    private List<ProfileStat> profileStats;

    @Data
    @ApiModel(description = "博主主页统计项")
    public static class ProfileStat {
        @ApiModelProperty(value = "统计项名称")
        private String label;

        @ApiModelProperty(value = "统计数值")
        private int count;

        public ProfileStat() {}

        public ProfileStat(String label, int count) {
            this.label = label;
            this.count = count;
        }
    }
}
