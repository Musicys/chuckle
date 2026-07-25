package com.yupi.springbootinit.model.dto.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户提交留言请求
 */
@ApiModel(description = "用户提交留言请求")
public class MessageAddRequest {

    @ApiModelProperty(value = "留言者昵称", required = true)
    private String nickname;

    @ApiModelProperty(value = "留言者邮箱（用于回复通知）")
    private String email;

    @ApiModelProperty(value = "留言者头像URL")
    private String avatar;

    @ApiModelProperty(value = "留言内容", required = true)
    private String content;

    @ApiModelProperty(value = "被回复的留言ID（不传则为一级留言）")
    private Integer replyToCommentId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyToCommentId() {
        return replyToCommentId;
    }

    public void setReplyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }
}
