package com.yupi.springbootinit.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问日志表
 * @TableName visit_logs
 */
@TableName(value ="visit_logs")
public class VisitLogs implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 访问者IP
     */
    private String ip;

    /**
     * User-Agent
     */
    private String userAgent;

    /**
     * 访问页面URL
     */
    private String pageUrl;

    /**
     * 访问日期（yyyy-MM-dd）
     */
    private Date visitDate;

    /**
     * 访问时间
     */
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 访问者IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 访问者IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * User-Agent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * User-Agent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 访问页面URL
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * 访问页面URL
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * 访问日期（yyyy-MM-dd）
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * 访问日期（yyyy-MM-dd）
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * 访问时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 访问时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        VisitLogs other = (VisitLogs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getUserAgent() == null ? other.getUserAgent() == null : this.getUserAgent().equals(other.getUserAgent()))
            && (this.getPageUrl() == null ? other.getPageUrl() == null : this.getPageUrl().equals(other.getPageUrl()))
            && (this.getVisitDate() == null ? other.getVisitDate() == null : this.getVisitDate().equals(other.getVisitDate()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getUserAgent() == null) ? 0 : getUserAgent().hashCode());
        result = prime * result + ((getPageUrl() == null) ? 0 : getPageUrl().hashCode());
        result = prime * result + ((getVisitDate() == null) ? 0 : getVisitDate().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ip=").append(ip);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", pageUrl=").append(pageUrl);
        sb.append(", visitDate=").append(visitDate);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
