package com.yupi.springbootinit.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 博主信息表
 * @TableName blogger_info
 */
@TableName(value ="blogger_info")
public class BloggerInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * QQ
     */
    private String qq;

    /**
     * GitHub 链接
     */
    private String github;

    /**
     * Gitee 链接
     */
    private String gitee;

    /**
     * Bilibili 链接
     */
    private String bilibili;

    /**
     * 出生年月
     */
    private Date birthDate;

    /**
     * 性别（0=保密 1=男 2=女）
     */
    private Integer gender;

    /**
     * 现职业
     */
    private String occupation;

    /**
     * 个性标签（JSON数组）
     */
    private String tags;

    /**
     * 爱好（JSON数组）
     */
    private String hobbies;

    /**
     * 爱好游戏（JSON数组）
     */
    private String games;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 文字地址
     */
    private String address;

    /**
     * 博客公告（Markdown格式）
     */
    private String announcement;

    /**
     * 个人配置（JSON格式）
     */
    private String config;

    /**
     * 是否启用（0=不使用 1=使用）
     */
    private Integer isUsed;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 头像URL
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像URL
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 博客标题
     */
    public String getBlogTitle() {
        return blogTitle;
    }

    /**
     * 博客标题
     */
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    /**
     * QQ
     */
    public String getQq() {
        return qq;
    }

    /**
     * QQ
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * GitHub 链接
     */
    public String getGithub() {
        return github;
    }

    /**
     * GitHub 链接
     */
    public void setGithub(String github) {
        this.github = github;
    }

    /**
     * Gitee 链接
     */
    public String getGitee() {
        return gitee;
    }

    /**
     * Gitee 链接
     */
    public void setGitee(String gitee) {
        this.gitee = gitee;
    }

    /**
     * Bilibili 链接
     */
    public String getBilibili() {
        return bilibili;
    }

    /**
     * Bilibili 链接
     */
    public void setBilibili(String bilibili) {
        this.bilibili = bilibili;
    }

    /**
     * 出生年月
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * 出生年月
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 性别（0=保密 1=男 2=女）
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 性别（0=保密 1=男 2=女）
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 现职业
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * 现职业
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * 个性标签（JSON数组）
     */
    public String getTags() {
        return tags;
    }

    /**
     * 个性标签（JSON数组）
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * 爱好（JSON数组）
     */
    public String getHobbies() {
        return hobbies;
    }

    /**
     * 爱好（JSON数组）
     */
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    /**
     * 爱好游戏（JSON数组）
     */
    public String getGames() {
        return games;
    }

    /**
     * 爱好游戏（JSON数组）
     */
    public void setGames(String games) {
        this.games = games;
    }

    /**
     * 纬度
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 纬度
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 经度
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 经度
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * 文字地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 文字地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 博客公告（Markdown格式）
     */
    public String getAnnouncement() {
        return announcement;
    }

    /**
     * 博客公告（Markdown格式）
     */
    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    /**
     * 个人配置（JSON格式）
     */
    public String getConfig() {
        return config;
    }

    /**
     * 个人配置（JSON格式）
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * 是否启用（0=不使用 1=使用）
     */
    public Integer getIsUsed() {
        return isUsed;
    }

    /**
     * 是否启用（0=不使用 1=使用）
     */
    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BloggerInfo other = (BloggerInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getBlogTitle() == null ? other.getBlogTitle() == null : this.getBlogTitle().equals(other.getBlogTitle()))
            && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
            && (this.getGithub() == null ? other.getGithub() == null : this.getGithub().equals(other.getGithub()))
            && (this.getGitee() == null ? other.getGitee() == null : this.getGitee().equals(other.getGitee()))
            && (this.getBilibili() == null ? other.getBilibili() == null : this.getBilibili().equals(other.getBilibili()))
            && (this.getBirthDate() == null ? other.getBirthDate() == null : this.getBirthDate().equals(other.getBirthDate()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getOccupation() == null ? other.getOccupation() == null : this.getOccupation().equals(other.getOccupation()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getHobbies() == null ? other.getHobbies() == null : this.getHobbies().equals(other.getHobbies()))
            && (this.getGames() == null ? other.getGames() == null : this.getGames().equals(other.getGames()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getAnnouncement() == null ? other.getAnnouncement() == null : this.getAnnouncement().equals(other.getAnnouncement()))
            && (this.getConfig() == null ? other.getConfig() == null : this.getConfig().equals(other.getConfig()))
            && (this.getIsUsed() == null ? other.getIsUsed() == null : this.getIsUsed().equals(other.getIsUsed()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getBlogTitle() == null) ? 0 : getBlogTitle().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getGithub() == null) ? 0 : getGithub().hashCode());
        result = prime * result + ((getGitee() == null) ? 0 : getGitee().hashCode());
        result = prime * result + ((getBilibili() == null) ? 0 : getBilibili().hashCode());
        result = prime * result + ((getBirthDate() == null) ? 0 : getBirthDate().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getOccupation() == null) ? 0 : getOccupation().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getHobbies() == null) ? 0 : getHobbies().hashCode());
        result = prime * result + ((getGames() == null) ? 0 : getGames().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getAnnouncement() == null) ? 0 : getAnnouncement().hashCode());
        result = prime * result + ((getConfig() == null) ? 0 : getConfig().hashCode());
        result = prime * result + ((getIsUsed() == null) ? 0 : getIsUsed().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", avatar=").append(avatar);
        sb.append(", nickname=").append(nickname);
        sb.append(", blogTitle=").append(blogTitle);
        sb.append(", qq=").append(qq);
        sb.append(", github=").append(github);
        sb.append(", gitee=").append(gitee);
        sb.append(", bilibili=").append(bilibili);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", gender=").append(gender);
        sb.append(", occupation=").append(occupation);
        sb.append(", tags=").append(tags);
        sb.append(", hobbies=").append(hobbies);
        sb.append(", games=").append(games);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", address=").append(address);
        sb.append(", announcement=").append(announcement);
        sb.append(", config=").append(config);
        sb.append(", isUsed=").append(isUsed);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
