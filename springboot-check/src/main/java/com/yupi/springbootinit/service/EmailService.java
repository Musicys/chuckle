package com.yupi.springbootinit.service;

/**
 * 邮件发送服务
 */
public interface EmailService {

    /**
     * 发送纯文本邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendTextEmail(String to, String subject, String content);
}
