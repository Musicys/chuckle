package com.yupi.springbootinit.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 图床文件表
 * @TableName images
 */
@TableName(value ="images")
public class Images implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 访问URL
     */
    private String url;

    /**
     * 存储路径（OSS key 或本地路径）
     */
    private String storagePath;

    /**
     * 文件大小（字节）
     */
    private Integer fileSize;

    /**
     * 文件类型（MIME，如 image/png）
     */
    private String fileType;

    /**
     * 图片宽度（像素）
     */
    private Integer width;

    /**
     * 图片高度（像素）
     */
    private Integer height;

    /**
     * 文件 MD5（去重）
     */
    private String md5;

    /**
     * 来源（article/avatar/etc）
     */
    private String source;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上传时间
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
     * 原始文件名
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * 原始文件名
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * 访问URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 访问URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 存储路径（OSS key 或本地路径）
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * 存储路径（OSS key 或本地路径）
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    /**
     * 文件大小（字节）
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * 文件大小（字节）
     */
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 文件类型（MIME，如 image/png）
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件类型（MIME，如 image/png）
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 图片宽度（像素）
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 图片宽度（像素）
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 图片高度（像素）
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 图片高度（像素）
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 文件 MD5（去重）
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 文件 MD5（去重）
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * 来源（article/avatar/etc）
     */
    public String getSource() {
        return source;
    }

    /**
     * 来源（article/avatar/etc）
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 上传时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 上传时间
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
        Images other = (Images) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOriginalName() == null ? other.getOriginalName() == null : this.getOriginalName().equals(other.getOriginalName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getStoragePath() == null ? other.getStoragePath() == null : this.getStoragePath().equals(other.getStoragePath()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getWidth() == null ? other.getWidth() == null : this.getWidth().equals(other.getWidth()))
            && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
            && (this.getMd5() == null ? other.getMd5() == null : this.getMd5().equals(other.getMd5()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOriginalName() == null) ? 0 : getOriginalName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getStoragePath() == null) ? 0 : getStoragePath().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getWidth() == null) ? 0 : getWidth().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getMd5() == null) ? 0 : getMd5().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
        sb.append(", originalName=").append(originalName);
        sb.append(", url=").append(url);
        sb.append(", storagePath=").append(storagePath);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileType=").append(fileType);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", md5=").append(md5);
        sb.append(", source=").append(source);
        sb.append(", remark=").append(remark);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
