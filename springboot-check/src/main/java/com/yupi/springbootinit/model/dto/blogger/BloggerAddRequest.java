package com.yupi.springbootinit.model.dto.blogger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 博主信息添加请求
 */
@ApiModel(description = "博主信息添加请求")
public class BloggerAddRequest {

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "昵称", required = true)
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

    @ApiModelProperty(value = "个人配置（JSON格式）")
    private String config;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getGitee() {
        return gitee;
    }

    public void setGitee(String gitee) {
        this.gitee = gitee;
    }

    public String getBilibili() {
        return bilibili;
    }

    public void setBilibili(String bilibili) {
        this.bilibili = bilibili;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
