package com.example.lixc.service;

import javax.mail.MessagingException;

public interface IAsyncService {

    /**
     * 异步发送邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlEmailAsync(String to, String subject, String content);

    /**
     * 异步发送邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendSimpleEmailAsync(String to, String subject, String content);

    /**
     * 发送带图片的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId   图片ID，用于在<img>标签中使用，从而显示图片
     */
    void sendImageEmailAsync(String to, String subject, String content, String rscPath, String rscId) throws MessagingException;
}
