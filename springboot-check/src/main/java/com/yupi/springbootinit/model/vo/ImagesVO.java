package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 图床文件视图
 */
@ApiModel(description = "图床文件视图")
public class ImagesVO {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "原始文件名")
    private String originalName;

    @ApiModelProperty(value = "访问URL")
    private String url;

    @ApiModelProperty(value = "文件大小（字节）")
    private Integer fileSize;

    @ApiModelProperty(value = "文件类型（MIME）")
    private String fileType;

    @ApiModelProperty(value = "图片宽度（像素）")
    private Integer width;

    @ApiModelProperty(value = "图片高度（像素）")
    private Integer height;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "上传时间")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
